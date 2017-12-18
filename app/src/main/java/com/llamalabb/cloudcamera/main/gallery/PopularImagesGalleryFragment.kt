package com.llamalabb.cloudcamera.main.gallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.llamalabb.cloudcamera.EqualSpaceItemDecoration
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.ktfiles.loadImage
import com.llamalabb.cloudcamera.model.DataManager
import com.llamalabb.cloudcamera.model.MyImage
import kotlinx.android.synthetic.main.fragment_gallery_recycler_view.*
import kotlinx.android.synthetic.main.fragment_gallery_recycler_view.view.*

/**
 * Created by andy on 12/18/17.
 */
class PopularImagesGalleryFragment : Fragment(), DataManager.DMCallBack{


    private var page: Int = 1
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments.getInt("pageNum", 0)
        title = arguments.getString("title")

        DataManager.userGalleryListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_gallery_recycler_view, container, false)
        val itemDecoration = EqualSpaceItemDecoration(context, R.dimen.item_spacing)
        view.gallery_recycler_view.layoutManager = GridLayoutManager(context, 2)
        view.gallery_recycler_view.addItemDecoration(itemDecoration)
        view.gallery_recycler_view.adapter = GalleryRecyclerAdapter(DataManager.popularList)
        return view
    }

    override fun dataSetChanged() {
        gallery_recycler_view?.let{ it.adapter.notifyDataSetChanged() }
    }


    companion object {
        fun newInstance(pageNum: Int, title: String) : PopularImagesGalleryFragment {
            val galFrag = PopularImagesGalleryFragment()
            val args = Bundle()
            args.putInt("pageNum", pageNum)
            args.putString("title", title)
            galFrag.arguments = args
            return galFrag
        }
    }

    class GalleryRecyclerAdapter(val imgUrls: ArrayList<MyImage>)
        : RecyclerView.Adapter<GalleryRecyclerAdapter.MyViewHolder>() {
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.image.loadImage(imgUrls[position].url)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return imgUrls.size
        }

        class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
            val image = view.findViewById<ImageView>(R.id.image_view)
        }
    }
}