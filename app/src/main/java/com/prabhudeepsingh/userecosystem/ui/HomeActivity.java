package com.prabhudeepsingh.userecosystem.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.prabhudeepsingh.userecosystem.R;
import com.prabhudeepsingh.userecosystem.adapter.UserAdapter;
import com.prabhudeepsingh.userecosystem.listener.RecyclerAdapterClickListener;
import com.prabhudeepsingh.userecosystem.model.Address;
import com.prabhudeepsingh.userecosystem.model.Song;
import com.prabhudeepsingh.userecosystem.model.User;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements RecyclerAdapterClickListener{

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    UserAdapter userAdapter;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    String uid;

    void initViews(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

//        ActionBar ab = getSupportActionBar();
//        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
//        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//
//        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService
//                (Context.LAYOUT_INFLATER_SERVICE);
//
//        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.WRAP_CONTENT,
//                ActionBar.LayoutParams.MATCH_PARENT,
//                Gravity.CENTER);
//
//        View view = inflater.inflate(R.layout.actionbar_layout, null);
//        TextView eTxt = view.findViewById(R.id.myText);
//        eTxt.setText("Custom Text");
//
//        ab.setCustomView(view, params);


        uid = auth.getUid();

        recyclerView = findViewById(R.id.recyclerViewHome);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //To delete data from firestore.
        //firestore.collection("users").document(uid).delete();
        //It is a Collection/Document/Collection/Document/Collection..... approach

        //addData();

        final ArrayList<User> userList = new ArrayList<>();

        Query query = firestore.collection("users").orderBy("name", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>().setQuery(query, User.class).build();


        userAdapter = new UserAdapter(options);
        userAdapter.startListening();
        userAdapter.setRecyclerAdapterClickListener(this);
        recyclerView.setAdapter(userAdapter);

//        query.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                for (DocumentSnapshot documentSnapshot : documentSnapshots){
//                    User user = documentSnapshot.toObject(User.class);
//                    userList.add(user);
//                    Toast.makeText(HomeActivity.this, ""+user.name, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    void addData(){
        CollectionReference collectionReference1 = firestore.collection("users").document(uid).collection("addresses");
        CollectionReference collectionReference2 = firestore.collection("users").document(uid).collection("songs");

        Address a1 = new Address("Room No 122", "Hostel No 1", "GNDEC, Ludhiana", "Punjab", 101006);
        Song s1 = new Song("Teri aankhiyan ka yo kajal", "Manpreet Pandher", 4);

        collectionReference1.add(a1).addOnSuccessListener(this, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String aid = documentReference.getId();
                Log.i("User", "Address Created with ID "+aid);
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Failure", "Address not added.");
                    }
                });

        collectionReference2.add(s1).addOnSuccessListener(this, new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String sid = documentReference.getId();
                Log.i("song", "Song created with id "+sid);
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Failure", "Song not added.");
                    }
                });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.option_items_home, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id){
//            case R.id.signOutItem : auth.signOut();
//                Toast.makeText(this, "Signed Out!", Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//
//            case R.id.launchMain: Intent intent1 = new Intent(HomeActivity.this, MainActivity.class);
//                    startActivity(intent1);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    @Override
    public void onRecyclerAdapterClickListener(int position) {
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
    }
}
