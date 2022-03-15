package ca.nscc.blackjack.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ca.nscc.blackjack.R;
import ca.nscc.blackjack.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    //keyboard dismiss vars
    private ConstraintLayout cLayout;
    private Context mContext;

    //fragment vars
    EditText playerName;
    Button submitName;
    SharedPreferences pref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //keyboard dismiss code
        mContext = getActivity().getApplicationContext();
        cLayout = binding.homeFragment;
        cLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        playerName = binding.playerName;
        submitName = binding.submitName;
        pref = getActivity().getSharedPreferences("user_data", MODE_PRIVATE);

        submitName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = playerName.getText().toString();

                if (!nameString.equals("")){//if name is not blank
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("playerName", nameString);
                    editor.commit();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}