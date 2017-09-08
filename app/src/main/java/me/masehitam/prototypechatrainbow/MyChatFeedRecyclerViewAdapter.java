package me.masehitam.prototypechatrainbow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.masehitam.prototypechatrainbow.ChatFeedFragment.OnListFragmentInteractionListener;
import me.masehitam.prototypechatrainbow.dummy.DummyContent.DummyItem;
import me.masehitam.prototypechatrainbow.model.ChatFeed;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyChatFeedRecyclerViewAdapter extends RecyclerView.Adapter<MyChatFeedRecyclerViewAdapter.ViewHolder> {

    private final List<ChatFeed> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyChatFeedRecyclerViewAdapter(List<ChatFeed> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String name = mValues.get(position).getName();
        holder.mTextName.setText(mValues.get(position).getName());
        holder.mTextLastMessage.setText(mValues.get(position).getLastMessage());
        holder.mTextTime.setText(mValues.get(position).getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onListFragmentInteraction(name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mTextName, mTextLastMessage, mTextTime;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mTextName = (TextView) view.findViewById(R.id.text_name);
            mTextLastMessage = (TextView) view.findViewById(R.id.text_message);
            mTextTime = (TextView) view.findViewById(R.id.text_time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextName.getText() + "'";
        }
    }
}
