package com.example.storeoverlay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.woosmap.woosmapsdk.WoosmapView
import com.woosmap.woosmapsdk.client.models.AssetFeatureResponse
import com.woosmap.woosmapsdk.overlays.OnStoreSelectedListener
import com.woosmap.woosmapsdk.overlays.StoresOverlay
import com.woosmap.woosmapsdk.overlays.StoresStyle

class MainActivity : AppCompatActivity(), OnStoreSelectedListener {
    lateinit var mapView: WoosmapView
    lateinit var storeDetails: TextView

    val baseStyle: String = """
        {
          rules: [
          ],
          default: {
            color: "black",
            icon: {
              url:
                "https://webapp-conf.woosmap.com/woos-0c78592f-13ea-362b-aa07-ba4ba9ea3dae/default.svg",
              scaledSize: {
                width: 24,
                height: 24
              },
              anchor: {
                x: 16,
                y: 16
              }
            },
            selectedIcon: {
              url:
                "https://webapp-conf.woosmap.com/woos-0c78592f-13ea-362b-aa07-ba4ba9ea3dae/selected.svg",
              scaledSize: {
                width: 32,
                height: 32
              },
              anchor: {
                x: 21,
                y: 21
              }
            }
          },
          breakPoint: 8
        }
        """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mapView = findViewById<WoosmapView>(R.id.mapView)
        storeDetails = findViewById<TextView>(R.id.storeDetails)
        storeDetails.text = "Store Details"

        val storesStyle = StoresStyle.buildFromJson(baseStyle)
        val overlay = StoresOverlay(mapView, storesStyle)
        overlay.addOnStoreSelectedListener(this)
    }

    override fun onStoreSelected(feature: AssetFeatureResponse) {
        feature.properties?.name?.let {
            storeDetails.text = it
        }
    }
}
