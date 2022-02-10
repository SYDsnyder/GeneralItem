package com.snyder.general

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.general_item_layout.view.*


class GeneralItemView : FrameLayout {

    private var itemClickListener: ItemClickListener? = null
    private var rightIconClickListener: RightIconClickListener? = null

    constructor(@NonNull context: Context) : super(context) {
        initView(context)
    }

    constructor(@NonNull context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
        initAttrs(context, attrs)
    }

    constructor(
        @NonNull context: Context,
        @Nullable attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initView(context)
        initAttrs(context, attrs)
    }

    //初始化View
    private fun initView(context: Context) {
        val view: View = LayoutInflater.from(context).inflate(R.layout.general_item_layout, null)
        //把自定义的这个组合控件的布局加入到当前FrameLayout
        addView(view)
        //item点击监听
        ll_general_item.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener!!.itemClick(this)
            }
        }
        //右边图标点击监听
        iv_right_icon.setOnClickListener {
            if (rightIconClickListener != null) {
                rightIconClickListener!!.onRightIconClick(this)
            }
        }
    }

    /**
     * 初始化相关属性
     *
     * @param context
     * @param attrs
     */
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        //默认字体大小，15sp
        val defaultSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            15f,
            context.resources.displayMetrics
        )
        //默认边距，15dp
        val defaultMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            15f,
            context.resources.displayMetrics
        )
        //默认标题字体颜色
        val defaultTitleColor = context.resources.getColor(R.color.black)
        //默认输入框字体颜色
        val defaultEdtColor = context.resources.getColor(R.color.c_33)
        //默认输入框中提示内容的字体颜色
        val defaultHintColor = context.resources.getColor(R.color.c_99)
        //默认下划线颜色
        val defaultLineColor = context.resources.getColor(R.color.c_ee)

        //通过obtainStyledAttributes方法获取到一个TypedArray对象，然后通过TypedArray对象就可以获取到相对应定义的属性值
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GeneralItem)
        val title = typedArray.getString(R.styleable.GeneralItem_title)
        val paddingHorizontal =
            typedArray.getDimension(R.styleable.GeneralItem_paddingHorizontal, defaultMargin)
        val paddingVertical =
            typedArray.getDimension(R.styleable.GeneralItem_paddingVertical, defaultMargin)
        val titleSize = typedArray.getDimension(R.styleable.GeneralItem_title_size, defaultSize)
        val titleColor = typedArray.getColor(R.styleable.GeneralItem_title_color, defaultTitleColor)
        val editContent = typedArray.getString(R.styleable.GeneralItem_edt_content)
        val editContentSize =
            typedArray.getDimension(R.styleable.GeneralItem_edt_text_size, defaultSize)
        val editContentColor =
            typedArray.getColor(R.styleable.GeneralItem_edt_text_color, defaultEdtColor)
        val editHintContent = typedArray.getString(R.styleable.GeneralItem_edt_hint_content)
        val editHintColor =
            typedArray.getColor(R.styleable.GeneralItem_edt_hint_text_color, defaultHintColor)

        val content = typedArray.getString(R.styleable.GeneralItem_right_content)
        val contentSize =
            typedArray.getDimension(R.styleable.GeneralItem_right_text_size, defaultSize)
        val contentColor =
            typedArray.getColor(R.styleable.GeneralItem_right_text_color, defaultEdtColor)
        val hintContent = typedArray.getString(R.styleable.GeneralItem_right_hint_content)
        val hintColor =
            typedArray.getColor(R.styleable.GeneralItem_right_hint_text_color, defaultHintColor)
        //标题是否带星
        val showStar = typedArray.getBoolean(R.styleable.GeneralItem_is_show_star, false)
        //标题是否加粗
        val isBold = typedArray.getBoolean(R.styleable.GeneralItem_title_is_bold, false)
        //默认不显示输入框
        val showEdit = typedArray.getBoolean(R.styleable.GeneralItem_is_show_edit, false)
        //默认显示右边文字
        val showContent = typedArray.getBoolean(R.styleable.GeneralItem_is_show_content, true)
        //默认输入框可以编辑
        val isEditable = typedArray.getBoolean(R.styleable.GeneralItem_isEditable, true)
        //右边图标是否可见，默认可见
        val showRightIcon = typedArray.getBoolean(R.styleable.GeneralItem_is_show_right_icon, true)
        val rightIcon =
            typedArray.getResourceId(R.styleable.GeneralItem_right_icon, R.mipmap.arrow_icon)
        //右边图标是否可见，默认不可见
        val showLeftIcon = typedArray.getBoolean(R.styleable.GeneralItem_is_show_left_icon, false)
        val leftIcon =
            typedArray.getResourceId(R.styleable.GeneralItem_left_icon, R.mipmap.arrow_icon)
        val showLine = typedArray.getBoolean(R.styleable.GeneralItem_is_show_line, true)
        val lineColor = typedArray.getColor(R.styleable.GeneralItem_line_color, defaultLineColor)
        val lineHeight = typedArray.getDimension(R.styleable.GeneralItem_line_height, 1f)

        typedArray.recycle()

        //判断右侧展示TextView还是EditText
        if (showEdit) {
            et_content.isVisible = true
            tv_content.isVisible = false
        } else {
            et_content.isVisible = false
            tv_content.isVisible = showContent
        }

        //设置下划线颜色和高度
        line.isVisible = showLine
        line.setBackgroundColor(lineColor)
        val params = line.layoutParams
        params.height = lineHeight.toInt()
        line.layoutParams = params

        //设置item的内边距
        ll_general_item.setPadding(
            paddingHorizontal.toInt(),
            paddingVertical.toInt(), paddingHorizontal.toInt(), paddingVertical.toInt()
        )
        //设置标题字体大小、颜色、是否加粗、是否带星
        if (showStar) {
            val spanContent = SpannableString("$title *")
            spanContent.setSpan(
                ForegroundColorSpan(Color.parseColor("#D0121B")),
                spanContent.length - 1, spanContent.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tv_title.text = spanContent
        } else {
            tv_title.text = title
        }
        if (isBold) {
            tv_title.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
        }
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize)
        tv_title.setTextColor(titleColor)

        //设置文字内容、字体大小、颜色
        tv_content.text = content
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentSize)
        tv_content.setTextColor(contentColor)
        tv_content.hint = hintContent
        tv_content.setHintTextColor(hintColor)

        et_content.setText(editContent)
        et_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, editContentSize)
        et_content.setTextColor(editContentColor)
        et_content.hint = editHintContent
        et_content.setHintTextColor(editHintColor)
        et_content.isFocusableInTouchMode = isEditable //设置输入框是否可以编辑
        et_content.isLongClickable = false //输入框不允许长按

        iv_right_icon.isVisible = showRightIcon //设置右边图标是否可见
        iv_right_icon.setImageResource(rightIcon)
        iv_left_icon.isVisible = showLeftIcon //设置左边图标是否可见
        iv_left_icon.setImageResource(leftIcon)
    }

    //Item点击事件监听
    interface ItemClickListener {
        fun itemClick(v: View?)
    }

    //右边图标点击事件监听
    interface RightIconClickListener {
        fun onRightIconClick(v: View?)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    fun setRightIconClickListener(rightIconClickListener: RightIconClickListener?) {
        this.rightIconClickListener = rightIconClickListener
    }

    //设置和获取右边文字内容
    var text: String?
        get() = tv_content.text.toString().trim { it <= ' ' }
        set(text) {
            tv_content.text = text
        }

    //设置和获取输入内容
    var editText: String?
        get() = et_content.text.toString().trim { it <= ' ' }
        set(text) {
            et_content.setText(text)
        }

}