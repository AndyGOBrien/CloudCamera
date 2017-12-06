package com.llamalabb.cloudcamera.main.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.main.FragmentCallBackContract
import kotlinx.android.synthetic.main.fragment_home.view.*


/**
 * Created by andy on 12/1/17.
 */
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
        view.logout_button.setOnClickListener { callBack.logout() }
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