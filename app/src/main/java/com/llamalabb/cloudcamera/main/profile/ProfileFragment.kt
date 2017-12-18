package com.llamalabb.cloudcamera.main.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.llamalabb.cloudcamera.R

/**
 * Created by andy on 12/1/17.
 */
class ProfileFragment : Fragment() {

    private var page: Int = 3
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments.getInt("pageNum", page)
        title = arguments.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    companion object {
        fun newInstance(pageNum: Int, title: String) : ProfileFragment {
            val profFrag = ProfileFragment()
            val args = Bundle()
            args.putInt("pageNum", pageNum)
            args.putString("title", title)
            profFrag.arguments = args
            return profFrag
        }
    }
}