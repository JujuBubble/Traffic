package com.z.traffic.feed.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.z.traffic.R
import com.z.traffic.feed.models.Camera
import kotlinx.android.synthetic.main.card_content_camera.view.*


class FeedAdapter internal constructor(private val picasso: Picasso) :
    RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val layout = R.layout.card_view_camera_details

    private var cameras: List<Camera>? = null

    fun setCameras(list: List<Camera>) {
        cameras = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        val viewHolder = ViewHolder(view, picasso)
        viewHolder.itemView.setOnClickListener{ v ->
            val context = v.context
            val intent = Intent(context, ImageViewActivity::class.java)
            cameras?.get(viewHolder.adapterPosition)?.image?.let {
                intent.putExtra(ImageViewActivity.IMAGE_URL_KEY, it)
            }
            context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cameras?.let { list ->
            holder.setItem(list[position])
        }
    }

    override fun getItemCount(): Int = cameras?.size ?: 0

    inner class ViewHolder(itemView: View, private val picasso: Picasso) :
        RecyclerView.ViewHolder(itemView) {

        fun setItem(item: Camera) {
            (item.image as? String)?.let { image ->
                picasso.load(image).into(itemView.cameraImageDetail)
            }

            itemView.cameraId.text = item.cameraId
            itemView.cameraLocation.text =
                LOCATION.format(item.location.latitude, item.location.longitude)
            itemView.cameraLastUpdated.text = item.timestamp
        }
    }

    companion object {
        private const val LOCATION = "Location: %.2f, %.2f"

    }
}
