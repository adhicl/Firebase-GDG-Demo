package com.aldoapps.yetanothereventapp;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private DatabaseReference menuRef = firebaseDatabase.getReference("menu");

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;

    private MenuAdapter menuAdapter;

    private List<RestoMenu> restoMenuList = new ArrayList<>();

    private ValueEventListener menuListener;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToNewMenu();
            }
        });

        initRecyclerView();
        initListener();
    }

    private void initListener() {
        menuListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                restoMenuList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RestoMenu restoMenu = snapshot.getValue(RestoMenu.class);
                    restoMenuList.add(restoMenu);
                }
                menuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false);
        rvMenu.setLayoutManager(linearLayoutManager);

        menuAdapter = new MenuAdapter(restoMenuList);
        rvMenu.setAdapter(menuAdapter);
    }

    private void navigateToNewMenu() {
        Intent intent = new Intent(this, NewMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            doSignOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doSignOut() {
        firebaseAuth.signOut();

        Intent intent = new Intent(this, LandingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void onDeleteMenuEvent(final DeleteMenuEvent menuEvent) {
        menuRef.child(menuEvent.getRestoMenu().getKey()).removeValue().addOnCompleteListener(this,
            new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(MainActivity.this,
                        "Berhasil menghapus " + menuEvent.getRestoMenu().getDescription(),
                        Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Subscribe
    public void onUpdateMenuEvent(UpdateMenuEvent menuEvent) {
        navigateToNewMenu(menuEvent.getRestoMenu());
    }

    private void navigateToNewMenu(RestoMenu restoMenu) {
        Intent intent = new Intent(this, NewMenuActivity.class);
        intent.putExtra(BundleKeys.MENU_KEY, restoMenu);
        startActivity(intent);
    }



    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        menuRef.removeEventListener(menuListener);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
        menuRef.addValueEventListener(menuListener);
    }


}
