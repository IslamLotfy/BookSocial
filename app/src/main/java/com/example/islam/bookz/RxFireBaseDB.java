package com.example.islam.bookz;

/**
 * Created by islam on 28/07/17.
 */


import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.kelvinapps.rxfirebase.exceptions.RxFirebaseDataException;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * Created by ahmedz on 11/7/16.
 */
public class RxFireBaseDB {

    @NonNull
    public static Observable<DataSnapshot> observeValueEvent(final Query query) {
        return Observable.create(new Observable.OnSubscribe<DataSnapshot>() {
            @Override
            public void call(final Subscriber<? super DataSnapshot> subscriber) {
                final ValueEventListener valueEventListener = query.addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext(dataSnapshot);
                                }
                            }

                            @Override
                            public void onCancelled(final DatabaseError error) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onError(new RxFirebaseDataException(error));
                                }
                            }
                        });
                subscriber.add(Subscriptions.create(() -> query.removeEventListener(valueEventListener)));
            }
        });
    }

    @NonNull
    public static Observable<DataSnapshot> observeSingleValueEvent(@NonNull final Query query) {
        return Observable.create(new Observable.OnSubscribe<DataSnapshot>() {
            @Override
            public void call(final Subscriber<? super DataSnapshot> subscriber) {
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(dataSnapshot);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(new RxFirebaseDataException(error));
                        }
                    }
                });
            }
        });
    }

    @NonNull
    public static Observable<RxFirebaseChildEvent<DataSnapshot>> observeChildEvent(
            @NonNull final Query query) {
        return Observable.create(new Observable.OnSubscribe<RxFirebaseChildEvent<DataSnapshot>>() {
            @Override
            public void call(final Subscriber<? super RxFirebaseChildEvent<DataSnapshot>> subscriber) {
                final ChildEventListener childEventListener = query.addChildEventListener(
                        new ChildEventListener() {

                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext(
                                            new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                    RxFirebaseChildEvent.EventType.ADDED));
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext(
                                            new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                    RxFirebaseChildEvent.EventType.CHANGED));
                                }
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext(new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot,
                                            RxFirebaseChildEvent.EventType.REMOVED));
                                }
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext(
                                            new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                    RxFirebaseChildEvent.EventType.MOVED));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onError(new RxFirebaseDataException(error));
                                }
                            }
                        });

                subscriber.add(Subscriptions.create(() -> query.removeEventListener(childEventListener)));
            }
        });
    }

    @NonNull
    public static <T> Observable<T> observeValueEvent(@NonNull final Query query,
                                                      @NonNull final Class<T> clazz) {
        return observeValueEvent(query, DataSnapshotMapper.of(clazz));
    }

    @NonNull
    public static <T> Observable<T> observeSingleValueEvent(@NonNull final Query query,
                                                            @NonNull final Class<T> clazz) {
        return observeSingleValueEvent(query, DataSnapshotMapper.of(clazz));
    }

    @NonNull
    public static <T> Observable<RxFirebaseChildEvent<T>> observeChildEvent(
            @NonNull final Query query, @NonNull final Class<T> clazz) {
        return observeChildEvent(query, DataSnapshotMapper.ofChildEvent(clazz));
    }

    @NonNull
    public static <T> Observable<T> observeValueEvent(@NonNull final Query query,
                                                      @NonNull final Func1<? super DataSnapshot, ? extends T> mapper) {
        return observeValueEvent(query).map(mapper);
    }

    @NonNull
    public static <T> Observable<T> observeSingleValueEvent(@NonNull final Query query,
                                                            @NonNull final Func1<? super DataSnapshot, ? extends T> mapper) {
        return observeSingleValueEvent(query).map(mapper);
    }

    @NonNull
    public static <T> Observable<RxFirebaseChildEvent<T>> observeChildEvent(
            @NonNull final Query query, @NonNull final Func1<? super RxFirebaseChildEvent<DataSnapshot>, ? extends RxFirebaseChildEvent<T>> mapper) {
        return observeChildEvent(query).map(mapper);
    }

    @NonNull
    public static Observable<Void> setValue(DatabaseReference reference, Object object) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                reference.setValue(object)
                        .addOnSuccessListener(aVoid -> {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            }
                        })
                        .addOnFailureListener(e -> {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(e);
                            }
                        });
            }
        });
    }

    public static Observable<Void> updateChildren(DatabaseReference reference, Map<String, Object> map) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                reference.updateChildren(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                            subscriber.onCompleted();
                        }
                    } else {
                        subscriber.onError(new Exception("Updating children was not successful!"));
                    }
                }).addOnFailureListener(e -> {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(e);
                    }
                });
            }
        });
    }

    public static Observable<Void> removeValue(DatabaseReference reference) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                reference.removeValue()
                        .addOnSuccessListener(aVoid -> {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            }
                        })
                        .addOnFailureListener(e -> {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(e);
                            }
                        });
            }
        });
    }
}