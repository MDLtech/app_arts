package com.example.ulsuart.ui.home

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint.Align
import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout.Alignment
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.ulsuart.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import kotlin.math.min


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        var count=0
        val sqlHHH = SqlHelp(requireContext())

        val main_values_ = sqlHHH.getArts(count)
        count+=1


        val mainTable = binding.MainTable
        val scrView = binding.scrollView2
        for (i in 0..(main_values_.size-1)/3){
            val tester = ImageView(requireContext())
            Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/${main_values_[i*3]}/full/843,/0/default.jpg").into(tester)
            //Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/bfb3ce18-337e-b8a5-56a3-6bf725d55c09/full/843,/0/default.jpg").into(tester)
            tester.setOnClickListener{
                //Toast.makeText(requireContext(),"Why u push me?",Toast.LENGTH_SHORT).show()
                val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
                val imageView = ImageView(context)
                Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/${main_values_[i*3]}/full/843,/0/default.jpg").into(imageView)

                // Добавляем кнопку закрытия
                val closeButton = Button(context)
                closeButton.text = "X"
                closeButton.width=10
                closeButton.setOnClickListener { dialog.dismiss() }

                // Создаем layout для ImageView и кнопки закрытия
                val layout = FrameLayout(requireContext())
                layout.addView(imageView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
                layout.addView(closeButton, FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.END or Gravity.TOP))

                dialog.setContentView(layout)
                dialog.show()






            }
            val layoutParams2 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams2.gravity = Gravity.START
            tester.layoutParams=layoutParams2
            tester.textAlignment= View.TEXT_ALIGNMENT_TEXT_END
            tester.scaleType=ImageView.ScaleType.CENTER_INSIDE
            val layoutParams3 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            layoutParams3.width=500
            layoutParams3.height=500
            tester.layoutParams=layoutParams3
            val miniCard = CardView(requireContext())
            val cardViewLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            cardViewLayoutParams.setMargins(20, 20, 20, 20)
            miniCard.layoutParams = cardViewLayoutParams
            miniCard.radius = 16F
            miniCard.setContentPadding(25, 25, 25, 25)
            miniCard.setCardBackgroundColor(Color.GRAY)
            miniCard.cardElevation = 8F
            miniCard.maxCardElevation = 12F
            val author = "${main_values_[i*3+1]}"
            val authorTextView = TextView(requireContext())
            val authorLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            authorLayoutParams.gravity = Gravity.END or Gravity.BOTTOM
            authorTextView.layoutParams = authorLayoutParams
            authorTextView.text = author
            authorTextView.setTypeface(null,Typeface.BOLD)

            val description = "${main_values_[i*3+2]}"
            val descriptionTextView = TextView(requireContext())
            val descriptionLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            descriptionLayoutParams.gravity = Gravity.START or Gravity.TOP
            descriptionTextView.layoutParams = descriptionLayoutParams
            descriptionTextView.text = description
            val contentLayout = LinearLayout(requireContext())
            contentLayout.orientation = LinearLayout.VERTICAL
            contentLayout.addView(descriptionTextView)
            contentLayout.addView(authorTextView)
            val cardViewLayoutParams2 = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            cardViewLayoutParams2.setMargins(20, 20, 20, 20)
            contentLayout.layoutParams = cardViewLayoutParams2
            val horizontalLayout = LinearLayout(requireContext())
            horizontalLayout.addView(tester)
            horizontalLayout.addView(contentLayout)
            miniCard.addView(horizontalLayout)
            mainTable.addView(miniCard)
        }

        mainTable.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrView.scrollY
            val totalHeight = mainTable.height
            val childCount = mainTable.childCount
            //println("$scrollY $totalHeight ${mainTable.getChildAt(childCount - 1).bottom}")

            if (scrollY + 2500 >= mainTable.getChildAt(childCount - 1).bottom) {
                // Достигнут конец LinearLayout
                // Добавьте свой код здесь



                val values_ = sqlHHH.getArts(count)
                count+=1
//                print("\"LULLUL\"")

                println("Create $count")
                for (i in 0..(values_.size-1)/3){
                    if (values_[i] == ""){
                        break
                    }

                    val tester = ImageView(requireContext())
                    Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/${values_[i*3]}/full/843,/0/default.jpg").into(tester)
                    //Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/bfb3ce18-337e-b8a5-56a3-6bf725d55c09/full/843,/0/default.jpg").into(tester)
                    tester.setOnClickListener{
                        //Toast.makeText(requireContext(),"Why u push me?",Toast.LENGTH_SHORT).show()
                        val dialog = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
                        val imageView = ImageView(context)
                        Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/${values_[i*3]}/full/843,/0/default.jpg").into(imageView)

                        // Добавляем кнопку закрытия
                        val closeButton = Button(context)
                        closeButton.text = "X"
                        closeButton.width=10
                        closeButton.setOnClickListener { dialog.dismiss() }

                        // Создаем layout для ImageView и кнопки закрытия
                        val layout = FrameLayout(requireContext())
                        layout.addView(imageView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
                        layout.addView(closeButton, FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.END or Gravity.TOP))

                        dialog.setContentView(layout)
                        dialog.show()






                    }
                    val layoutParams2 = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)

                    layoutParams2.gravity = Gravity.START
                    tester.layoutParams=layoutParams2
                    tester.textAlignment= View.TEXT_ALIGNMENT_TEXT_END
                    tester.scaleType=ImageView.ScaleType.CENTER_INSIDE

                    val layoutParams3 = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams3.width=500
                    layoutParams3.height=500
                    tester.layoutParams=layoutParams3
//
//
//
//
//                    val txt1=TextView(requireContext())
//                    txt1.text="Author"
//                    txt1.setPadding(10,10,0,0)
//                    miniCard.addView(txt1)
//
//
//
//
//                    miniCard.addView(tester)
//                    mainTable.addView(miniCard)

                    //---------------
//                    val miniCard= CardView(requireContext())
//                    val layou= LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
//                    layou.setMargins(20,20,20,20)
//                    miniCard.radius=16F
//                    miniCard.layoutParams=layou
//                    miniCard.setContentPadding(25,25,25,25)
//                    miniCard.setCardBackgroundColor(Color.GRAY)
//                    miniCard.cardElevation=8F
//                    miniCard.maxCardElevation=12F
//
//                    val tester = ImageView(requireContext())
//                    Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/bfb3ce18-337e-b8a5-56a3-6bf725d55c09/full/843,/0/default.jpg").into(tester)
//                    tester.setOnClickListener{
//                        Toast.makeText(requireContext(),"Why u push me?",Toast.LENGTH_SHORT).show()
//                    }
//                    val layoutParams2 = LinearLayout.LayoutParams(
//                        0,
//                        LinearLayout.LayoutParams.WRAP_CONTENT)
//
//                    layoutParams2.weight = 1f
//                    tester.layoutParams=layoutParams2
//                    tester.textAlignment= View.TEXT_ALIGNMENT_TEXT_END
//                    tester.scaleType=ImageView.ScaleType.CENTER_INSIDE
//
//                    val layoutParams3 = LinearLayout.LayoutParams(
//                        500,
//                        500)
//                    tester.layoutParams=layoutParams3
//
//
//                    val layoutParams4 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
//                    layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//                    layoutParams4.addRule(RelativeLayout.ALIGN_PARENT_END)
//
//
//                    val txt1=TextView(requireContext())
//                    //txt1.textAlignment= View.TEXT_ALIGNMENT_TEXT_END
//                    txt1.layoutParams =layoutParams4
//                    txt1.text="Author"
//                    //txt1.setPadding(10,10,0,0)
//                    miniCard.addView(tester)
//                    miniCard.addView(txt1)
//
//                    mainTable.addView(miniCard)
                    val miniCard = CardView(requireContext())

// LayoutParams для всей CardView
                    val cardViewLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    cardViewLayoutParams.setMargins(20, 20, 20, 20)
                    miniCard.layoutParams = cardViewLayoutParams

                    miniCard.radius = 16F
                    miniCard.setContentPadding(25, 25, 25, 25)
                    miniCard.setCardBackgroundColor(Color.GRAY)
                    miniCard.cardElevation = 8F
                    miniCard.maxCardElevation = 12F

// ImageView с использованием библиотеки Picasso
                    //val str = "https://www.artic.edu/iiif/2/bfb3ce18-337e-b8a5-56a3-6bf725d55c09/full/843,/0/default.jpg"
//                    val tester = ImageView(requireContext())
//                    Picasso.with(requireContext()).load("https://www.artic.edu/iiif/2/bfb3ce18-337e-b8a5-56a3-6bf725d55c09/full/843,/0/default.jpg").resize(500, 500).centerCrop().into(tester)
//                    val testerLayoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
//                    testerLayoutParams.weight = 1f
//                    tester.layoutParams = testerLayoutParams
//                    tester.scaleType = ImageView.ScaleType.CENTER_CROP

// TextView для автора
                    val author = "${values_[i*3+1]}"
                    val authorTextView = TextView(requireContext())
                    val authorLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    authorLayoutParams.gravity = Gravity.END or Gravity.BOTTOM
                    authorTextView.layoutParams = authorLayoutParams
                    authorTextView.text = author
                    authorTextView.setTypeface(null,Typeface.BOLD)

// TextView для описания
                    val description = "${values_[i*3+2]}"
                    val descriptionTextView = TextView(requireContext())
                    val descriptionLayoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    descriptionLayoutParams.gravity = Gravity.START or Gravity.TOP
                    descriptionTextView.layoutParams = descriptionLayoutParams
                    descriptionTextView.text = description

// Добавляем ImageView и TextView в CardView
                    val contentLayout = LinearLayout(requireContext())
                    contentLayout.orientation = LinearLayout.VERTICAL
                    contentLayout.addView(descriptionTextView)

                    contentLayout.addView(authorTextView)

                    val cardViewLayoutParams2 = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    cardViewLayoutParams2.setMargins(20, 20, 20, 20)
                    contentLayout.layoutParams = cardViewLayoutParams2

                    val horizontalLayout = LinearLayout(requireContext())
                    horizontalLayout.addView(tester)
                    horizontalLayout.addView(contentLayout)

                    //miniCard.addView(tester)
                    miniCard.addView(horizontalLayout)
                    //miniCard.addView(authorTextView)
                    mainTable.addView(miniCard)



                }


            }
        }




        return root



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}