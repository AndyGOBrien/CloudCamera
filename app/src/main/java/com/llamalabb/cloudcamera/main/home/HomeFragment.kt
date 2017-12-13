package com.llamalabb.cloudcamera.main.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.main.FragmentCallBackContract
import com.llamalabb.cloudcamera.model.DataManager
import com.llamalabb.cloudcamera.model.MyImage
import com.llamalabb.cloudcamera.model.VoteCard
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.mindorks.placeholderview.SwipeViewBuilder
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private var page: Int = 0
    private var title: String = ""

    private lateinit var callBack: FragmentCallBackContract.HomeCallBack


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments.getInt("pageNum", page)
        title = arguments.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val swipeView = view.swipe_view
        swipeView.getBuilder<SwipePlaceHolderView, SwipeViewBuilder<SwipePlaceHolderView>>()
                .setDisplayViewCount(3)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.vote_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.vote_swipe_out_msg_view))



        if(DataManager.voteList.isNotEmpty()) {
            val tempList = DataManager.voteList
            val voteList = ArrayList<MyImage>()
            val user = FirebaseAuth.getInstance().currentUser
            tempList.forEach{
                if(!(it.upvotes.contains(user?.uid) || it.downvotes.contains(user?.uid)))
                    voteList.add(it)
            }
            voteList.forEach { swipeView.addView(VoteCard(context, it, swipeView)) }
        }

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callBack = context as FragmentCallBackContract.HomeCallBack
    }

    companion object {
        fun newInstance(pageNum: Int, title: String) : HomeFragment {
            val homeFrag = HomeFragment()
            val args = Bundle()
            args.putInt("pageNum", pageNum)
            args.putString("title", title)
            homeFrag.arguments = args
            return homeFrag
        }
    }
}