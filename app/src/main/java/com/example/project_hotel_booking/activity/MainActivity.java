    package com.example.project_hotel_booking.activity;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;
    import androidx.viewpager2.widget.ViewPager2;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.example.project_hotel_booking.Adapter.ViewPagerAdapter;
    import com.example.project_hotel_booking.Fragments.bookroom.ConfirmationFragment;
    import com.example.project_hotel_booking.R;
    import com.google.android.material.tabs.TabLayout;
    import com.google.android.material.tabs.TabLayoutMediator;

    public class MainActivity extends AppCompatActivity  {
        androidx.appcompat.widget.Toolbar toolbar;
        private ViewPager2 viewPager;
        private TabLayout tabLayout;
        private boolean isSelectRoomFragmentVisible = true; // Flag to track the visibility of SelectRoomFragment

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            toolbar =  findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            viewPager = findViewById(R.id.viewPager);
            tabLayout = findViewById(R.id.tabLayout);

            viewPager.setAdapter(new ViewPagerAdapter(this));

            int[] tabIcons = {R.drawable.room_list, R.drawable.confirm_bookings,R.drawable.booking_list, R.drawable.drawable_profile};
            String[] tabTitles = {"", "","" ,""};

            new TabLayoutMediator(tabLayout, viewPager,
                    (tab, position) ->
                    {
                        View customTabView = LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);

                        ImageView tabIcon = customTabView.findViewById(R.id.tabIcon);
                        TextView tabTitle = customTabView.findViewById(R.id.tabTitle);

                        tabIcon.setImageResource(tabIcons[position]); // Set the icon
                        tabTitle.setText(tabTitles[position]); // Set the title

                        tab.setCustomView(customTabView);
                    }).attach();

        }



        @Override
        public void onBackPressed() {
            if (!isSelectRoomFragmentVisible) {
                // If the SelectRoomFragment is not visible, make it visible and hide others
                navigateToSelectRoomFragment(); // Call the method to navigate to SelectRoomFragment
            } else {
                super.onBackPressed();
            }
        }


        // Method to navigate back to Fragment1 (SelectRoomFragment)
        public void navigateToSelectRoomFragment() {
            isSelectRoomFragmentVisible = true;
            viewPager.setCurrentItem(0); // Switch to the first tab (Fragment1)
        }

        // Method to navigate to other fragments (for example, ConfirmationMessageFragment)
        public void navigateToOtherFragment(Fragment fragment) {
            isSelectRoomFragmentVisible = false;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerFrameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.menu_item_1) {
                // Handle the Log Out menu item click
                // Add your logout logic here
    // For example, start a LoginActivity
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(intent);
                finish();

                return true;
            }
            else if (id == android.R.id.home) {
                // Handle the Up button click (back arrow)
                onBackPressed(); // This will navigate back to the previous fragment
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        // Method to show/hide the ConfirmationFragment
        public void showConfirmationFragment(boolean isVisible) {
            if (isVisible) {
                // Show the ConfirmationFragment
                // You can implement this logic based on your needs
                // For example, using FragmentTransaction
                // For example:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.containerFrameLayout, new ConfirmationFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                // Hide the ConfirmationFragment
                // You can implement this logic based on your needs
                // For example, using FragmentTransaction
                // For example:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.containerFrameLayout);
                if (fragment instanceof ConfirmationFragment) {
                    transaction.remove(fragment);
                    transaction.commit();
                }
            }
        }

    }
