package com.llamalabb.cloudcamera.main.photo


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.main.FragmentCallBackContract
import kotlinx.android.synthetic.main.fragment_add_photo.view.*

/**
 * Created by andy on 12/1/17.
 */
class AddPhotoFragment : Fragment() {

    private lateinit var callBack: FragmentCallBackContract.AddPhotoCallBack

    private var page: Int = 2
    private var title: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments.getInt("pageNum", page)
        title = arguments.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_add_photo, container, false)
        view.gallery_button.setOnClickListener{ callBack.launchGallery() }
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callBack = context as FragmentCallBackContract.AddPhotoCallBack
    }

    companion object {
        fun newInstance(pageNum: Int, title: String) : AddPhotoFragment{
            val addPhotoFrag = AddPhotoFragment()
            val args = Bundle()
            args.putInt("pageNum", pageNum)
            args.putString("title", title)
            addPhotoFrag.arguments = args
            return addPhotoFrag
        }
    }
}