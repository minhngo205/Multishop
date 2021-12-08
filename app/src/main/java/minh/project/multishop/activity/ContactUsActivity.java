package minh.project.multishop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import minh.project.multishop.databinding.ActivityContactUsBinding;

public class ContactUsActivity extends AppCompatActivity {

    private ActivityContactUsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.title.tvTitle.setText("Về chúng tôi");
    }
}