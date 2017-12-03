package com.llamalabb.cloudcamera.main.gallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.llamalabb.cloudcamera.R
import com.llamalabb.cloudcamera.ktfiles.loadImage
import kotlinx.android.synthetic.main.fragment_gallery.view.*


/**
 * Created by andy on 12/1/17.
 */
class GalleryFragment : Fragment(){

    private var page: Int = 1
    private var title: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments.getInt("pageNum", page)
        title = arguments.getString("title")


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        view.gallery_recycler.adapter = GalleryRecyclerAdapter()
        return view
    }


    companion object {
        fun newInstance(pageNum: Int, title: String) : GalleryFragment {
            val galFrag = GalleryFragment()
            val args = Bundle()
            args.putInt("pageNum", pageNum)
            args.putString("title", title)
            galFrag.arguments = args
            return galFrag
        }
    }


    class GalleryRecyclerAdapter(val imgUrls: List<String>) : RecyclerView.Adapter<GalleryRecyclerAdapter.MyViewHolder>() {
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.image.loadImage(imgUrls[position])
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