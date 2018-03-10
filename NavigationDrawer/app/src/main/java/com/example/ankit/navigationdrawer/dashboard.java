package com.example.ankit.navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.views.BannerSlider;

public class dashboard extends Fragment {
    private List<Event> eventList;//List to hold products
    DatabaseReference mDataRef;
    RecyclerView recyclerView;
    EventAdapter adapter;//adapter to link products

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dashboard,container,false);
        List<Banner> banners=new ArrayList<>();
        BannerSlider bannerSlider = view.findViewById(R.id.banner);
        //add banner using image url
        //banners.add(new RemoteBanner("Put banner image url here ..."));
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.one));
        banners.add(new DrawableBanner(R.drawable.two));
        banners.add(new DrawableBanner(R.drawable.three));
        bannerSlider.setBanners(banners);

        //Event card data
        eventList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //creating recyclerview adapter
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Events");
        mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String desc = (String)dataSnapshot1.child("eDescription").getValue();
                    String title = (String)dataSnapshot1.child("eTitle").getValue();
                    String img = (String)dataSnapshot1.child("eImgurl").getValue();
                    String id =dataSnapshot1.getKey();
                    eventList.add(new Event(title,desc,img,id));
                }
                adapter = new EventAdapter(getContext(),eventList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error:"+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        return view;

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Dashboard");

    }
}



/*
FirebaseUser user;
    DatabaseReference databaseReference;
    TextView text;
    public SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;

    List<Blog> list = new ArrayList<>();
    List<String> stringList = new ArrayList<>();

    RecyclerView recyclerView ;

    RecyclerView.Adapter adapter ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        text = (TextView)findViewById(R.id.delete);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("root").child("Blog");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Blog blog = dataSnapshot.getValue(Blog.class);
                    String s = dataSnapshot.getKey();
                    stringList.add(s);
                    list.add(blog);

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }


        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);

        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setIconSize(56,36);
        navigation.setTextSize(0);



    }

            return false;
        }
    };
 */