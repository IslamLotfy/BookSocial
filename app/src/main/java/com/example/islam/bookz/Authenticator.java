package com.example.islam.bookz;

/**
 * Created by islam on 28/07/17.
 */

import android.app.Activity;
import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.kelvinapps.rxfirebase.RxFirebaseUser;

import rx.Observable;

import static com.google.firebase.auth.FirebaseAuth.AuthStateListener;


/**
 * Created by ahmedz on 11/6/16.
 */

public class Authenticator {
    private static Authenticator instance;
    private FirebaseAuth mAuth;

    private Authenticator() {
        mAuth = FirebaseAuth.getInstance();
    }

    public synchronized static Authenticator getInstance() {
        if (instance == null)
            instance = new Authenticator();
        return instance;
    }

//    public void grantAccess(Activity activity) {
//        Toast.makeText(activity, activity.getString(R.string.welcome), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(activity, ContentActivity.class);
//        activity.startActivity(intent);
//        activity.finish();
//    }

    public void signIn(Activity activity, String email, String password, OnCompleteListener<AuthResult> onCompletionListener) {
        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener(e -> e.printStackTrace())
                .addOnCompleteListener(activity, onCompletionListener);
    }

    public boolean verifyAuth() {
        boolean sessionExists = mAuth.getCurrentUser() != null;
        return sessionExists;
    }

    public Observable<Object> createUser(Activity activity, String email, String password) {
        //create user
        return Observable.create(subscriber -> {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (!subscriber.isUnsubscribed()) {
                            if (task.isSuccessful()) {
                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new Exception("Task was not successful!"));
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (!subscriber.isUnsubscribed())
                            subscriber.onError(e);
                    });
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Void> updateUserProfile(UserProfileChangeRequest profileUpdates) {
        return RxFirebaseUser.updateProfile(mAuth.getCurrentUser(), profileUpdates);
    }

    public void addAuthListener(AuthStateListener authListener) {
        if (authListener != null)
            mAuth.addAuthStateListener(authListener);
    }

    public void removeAuthListener(AuthStateListener authListener) {
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }



    public String getUserEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user == null? "": user.getEmail();
    }

    public String getUserID() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user == null ? "" : user.getUid();
    }

    public Uri getPhotoUri() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user == null? null: user.getPhotoUrl();
    }

    public Observable<Object> signInWithCredential(AuthCredential credential) {
        return Observable.just(null);
//		return Observable.create(subscriber -> {
//			mAuth.signInWithCredential(credential)
//					.addOnCompleteListener(task -> {
//						if (!subscriber.isUnsubscribed()) {
//							if (task.isSuccessful()) {
//								subscriber.onNext(null);
//								subscriber.onCompleted();
//							} else {
//								subscriber.onError(new Exception("Task was not successful!"));
//							}
//						}
//					})
//					.addOnFailureListener(e -> {
//						if (!subscriber.isUnsubscribed())
//							subscriber.onError(e);
//					});
//		});
    }

//    public Observable<FacebookUserModel> getFacebookUser(AccessToken token) {
//        return Observable.create(subscriber -> {
//            if (!subscriber.isUnsubscribed()) {
//                Bundle params = new Bundle();
//                params.putString("fields", "picture.type(large),name,email,gender");
//                new GraphRequest(token, "me", params, HttpMethod.GET, response -> {
//                    try {
//                        JSONObject data = response.getJSONObject();
//                        if (data.has("picture")) {
//                            String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
//                            String username = data.getString("name");
//                            String email = data.getString("email");
//                            int gender = data.getString("gender").equals("male")? MALE: FEMALE;
//                            FacebookUserModel facebookUser = new FacebookUserModel(profilePicUrl, username, email, gender);
//                            subscriber.onNext(facebookUser);
//                            subscriber.onCompleted();
//                        }
//                    } catch (Exception e) {
//                        subscriber.onError(e);
//                    }
//                }).executeAsync();
//            }
//        });
//    }
//
//    public Observable<GoogleSignInAccount> signInWithGoogleAuth(Activity activity, Intent signInIntent) {
//        return RxActivityResult.on(activity)
//                .startIntent(signInIntent)
//                .filter(result -> result.resultCode() == RESULT_OK)
//                .flatMap(result -> {
//                    Intent data = result.data();
//                    GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//                    GoogleSignInAccount googleAccount = signInResult.getSignInAccount();
//                    AuthCredential credential = GoogleAuthProvider.getCredential(googleAccount.getIdToken(), null);
//                    return signInWithCredential(credential)
//                            .map(o -> googleAccount);
//                });
//    }

    public Observable<Object> resetPassword(String userEmail) {
        return Observable.create(subscriber -> {
            mAuth.sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener(task -> {
                        if (!subscriber.isUnsubscribed()) {
                            if (task.isSuccessful()) {
                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(new Exception("Task was not successful!"));
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        if (!subscriber.isUnsubscribed())
                            subscriber.onError(e);
                    });
        });
    }
}