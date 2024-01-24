import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_22_memorite.R
import com.example.guru2_22_memorite.ThreeButtons

class library_main : AppCompatActivity() {
    private var nextCellIndex = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library_main)

        // Spinner 초기화
        val spinner: Spinner = findViewById(R.id.filtering)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.filtering,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val firstCellButton: Button = findViewById(R.id.bookShelf1)
        firstCellButton.setOnClickListener {
            // 첫 번째 버튼 클릭 시 실행되는 로직
            addNewButton()
        }
    }

    // library_main_menu 목록 - 캘린더 메뉴바로 연결
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.library_main_menu, menu)
        return true
    }
    // 인아님 캘린더 페이지 이름이랑 연결해서 menu item 클릭하면 화면 전환되도록.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_library -> {
                val intent = Intent(this, library_main::class.java)
                startActivity(intent)
                return true
            }
//            R.id.action_calendar -> {
//                val intent = Intent(this, calendar_main::class.java) //인아님 페이지(캘린더 페이지 이름으로 변경)
//                startActivity(intent)
//                return true
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val onNewButtonClickListener = View.OnClickListener {
        // 동적으로 추가된 버튼 클릭 시 실행되는 로직
        addNewButton()
    }

    private fun addNewButton() {
        val newButton = Button(this)
        newButton.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        newButton.text = "버튼 $nextCellIndex"
        newButton.setOnClickListener {
            // 새로운 버튼 클릭 시 실행되는 로직
            if (nextCellIndex == 4) {
                openThreeButtonsActivity()
            } else {
                // 기존 로직 그대로 유지
                // 현재 액티비티의 레이아웃에 새로운 버튼을 추가
                val tableLayout: TableLayout = findViewById(R.id.bookShelfTable)
                val firstRow: TableRow = tableLayout.findViewById(R.id.firstRow)
                firstRow.addView(newButton)

                // 현재 버튼을 비활성화하고 새로운 버튼이 추가될 때만 보이도록 설정
                val currentButton: Button = firstRow.getChildAt(0) as Button
                currentButton.visibility = View.INVISIBLE
                newButton.visibility = View.VISIBLE

                nextCellIndex++
            }
        }
    }

    private fun openThreeButtonsActivity() {
        val intent = Intent(this, ThreeButtons::class.java)
        startActivity(intent)
    }

}
