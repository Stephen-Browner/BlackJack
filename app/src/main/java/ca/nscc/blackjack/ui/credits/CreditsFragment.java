package ca.nscc.blackjack.ui.credits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.nscc.blackjack.databinding.FragmentCreditsBinding;


public class CreditsFragment extends Fragment {

    private CreditsViewModel creditsViewModel;
    private FragmentCreditsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        creditsViewModel =
                new ViewModelProvider(this).get(CreditsViewModel.class);

        binding = FragmentCreditsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}