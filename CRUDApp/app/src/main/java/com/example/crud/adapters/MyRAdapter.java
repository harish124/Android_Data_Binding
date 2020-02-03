package com.example.crud.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.crud.MainActivity;
import com.example.crud.R;
import com.example.crud.UpdateActivity;
import com.example.crud.asyncTasks.GetDelete;
import com.example.crud.databinding.ActivityMainBinding;
import com.example.crud.databinding.RecyclerViewRowBinding;
import com.example.crud.frame_transition.Transition;
import com.example.crud.models.User;
import com.example.crud.print.Print;
import com.example.crud.view_model.MainActivityViewModel;

import java.util.ArrayList;



public class MyRAdapter extends RecyclerView.Adapter<MyRAdapter.UserViewHolder> {

    private Context mCtx;
    private Print p;
    private Transition transition;
    private ArrayList<User> usersList;
    private RecyclerViewRowBinding binding;
    private MainActivityViewModel vm;

    public MyRAdapter(Context mCtx, ArrayList<User> productList,MainActivityViewModel vm){
        this.mCtx = mCtx;
        this.usersList = productList;
        this.vm=vm;
        p=new Print(mCtx);
        transition=new Transition(mCtx);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.recycler_view_row,parent,
                false);



        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user=usersList.get(position);
        //p.sprintf("onBindView:\nName: "+user.getName());

        holder.binding.setUser(user);
        holder.binding.uid.setText(""+user.getUid());

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewRowBinding binding;
        public UserViewHolder(@NonNull RecyclerViewRowBinding itemView) {
            super(itemView.getRoot());

            binding=itemView;

            binding.expand.setOnClickListener((v)->{

                TransitionManager.beginDelayedTransition(binding.cardView,new AutoTransition());
                if(binding.linearLayout.getVisibility()==View.GONE){
                    binding.expand.setBackgroundResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    binding.linearLayout.setVisibility(View.VISIBLE);
                }
                else{
                    binding.expand.setBackgroundResource(R.drawable.ic_arrow_drop_down_black_24dp);
                    binding.linearLayout.setVisibility(View.GONE);
                }

            });

            binding.imageView.setOnClickListener((v)->{
                Intent intent=new Intent(mCtx,UpdateActivity.class);
                intent.putExtra("name",binding.name.getText());
                intent.putExtra("addr",binding.addr.getText());
                intent.putExtra("email",binding.email.getText());
                intent.putExtra("phno",binding.phno.getText());
                intent.putExtra("uid",binding.uid.getText());

                mCtx.startActivity(intent);
            });

            binding.deleteBtn.setOnClickListener((v)->{
                new GetDelete(mCtx,Integer.parseInt(binding.uid.getText().toString().trim()),vm)
                .execute();
            });
        }

    }
}
