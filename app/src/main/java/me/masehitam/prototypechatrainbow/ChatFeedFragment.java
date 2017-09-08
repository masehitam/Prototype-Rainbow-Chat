package me.masehitam.prototypechatrainbow;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ale.infra.list.ArrayItemList;
import com.ale.infra.list.IItemListChangeListener;
import com.ale.infra.proxy.conversation.IRainbowConversation;
import com.ale.rainbowsdk.RainbowSdk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.masehitam.prototypechatrainbow.model.ChatFeed;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ChatFeedFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String DATE_FORMAT = "MM-dd-yyyy";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private List<ChatFeed> chatFeeds = new ArrayList<>();
    private AppCompatActivity mActivity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatFeedFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChatFeedFragment newInstance(int columnCount) {
        ChatFeedFragment fragment = new ChatFeedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RainbowSdk.instance().conversations().getAllConversations().registerChangeListener(m_changeListener);
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        Log.d("lnx", "onCreateView: ");
        mActivity = (AppCompatActivity) getActivity();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyChatFeedRecyclerViewAdapter(chatFeeds, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String name);
    }

    private IItemListChangeListener m_changeListener = new IItemListChangeListener() {
        @Override
        public void dataChanged() {
            updateConversations();
        }
    };

    @Override
    public void onDestroyView() {
        RainbowSdk.instance().conversations().getAllConversations().unregisterChangeListener(m_changeListener);
        super.onDestroyView();
    }

    private void updateConversations() {
        ArrayItemList<IRainbowConversation> conversations = RainbowSdk.instance().conversations().getAllConversations();
        chatFeeds.clear();
        for (IRainbowConversation rainbow : conversations.getItems()) {
            chatFeeds.add(new ChatFeed(
                    rainbow.getLastMessage().getContactJid(),
                    rainbow.getLastMessage().getMessageContent(),
                    new SimpleDateFormat(DATE_FORMAT, Locale.US).format(new Date(rainbow.getLastMessage().getTimeStamp())))
            );
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }
}
