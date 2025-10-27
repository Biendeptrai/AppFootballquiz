package com.example.footballquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "football_quiz.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_SCORES = "scores";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 🧩 Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng câu hỏi
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT, " +
                "option1 TEXT, " +
                "option2 TEXT, " +
                "option3 TEXT, " +
                "option4 TEXT, " +
                "correctAnswer INTEGER)");

        // Bảng lưu điểm
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SCORES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "playerName TEXT, " +
                "score INTEGER)");

        // Gọi hàm chèn câu hỏi mặc định
        insertQuestionData(db);
    }

    // 🧹 Khi nâng version → xóa & tạo lại
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    // 🧠 Thêm 50 câu hỏi mặc định
    private void insertQuestionData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_QUESTIONS +
                " (question, option1, option2, option3, option4, correctAnswer) VALUES " +
                "('Đội nào vô địch World Cup 2022?', 'Pháp', 'Argentina', 'Brazil', 'Đức', 2)," +
                "('Cầu thủ nào giành Quả bóng vàng 2021?', 'Cristiano Ronaldo', 'Lionel Messi', 'Lewandowski', 'Mbappe', 2)," +
                "('Đội tuyển nào vô địch Euro 2016?', 'Pháp', 'Bồ Đào Nha', 'Đức', 'Anh', 2)," +
                "('Ai là cầu thủ ghi nhiều bàn nhất lịch sử World Cup?', 'Klose', 'Messi', 'Ronaldo', 'Pele', 1)," +
                "('Sân vận động Old Trafford thuộc CLB nào?', 'Liverpool', 'Manchester United', 'Chelsea', 'Arsenal', 2)," +
                "('Ai là cầu thủ đắt giá nhất thế giới 2024?', 'Mbappe', 'Bellingham', 'Haaland', 'Vinicius Jr', 2)," +
                "('Đội tuyển nào vô địch Euro 2020?', 'Anh', 'Ý', 'Pháp', 'Tây Ban Nha', 2)," +
                "('Ai là cầu thủ trẻ nhất ghi bàn tại World Cup?', 'Pele', 'Mbappe', 'Messi', 'Haaland', 1)," +
                "('Ai là HLV của đội tuyển Việt Nam năm 2024?', 'Park Hang-seo', 'Philippe Troussier', 'Miura', 'Hữu Thắng', 2)," +
                "('Đội bóng nào có biệt danh The Blues?', 'Liverpool', 'Arsenal', 'Chelsea', 'Man City', 3)," +
                "('Ai giành Chiếc giày vàng World Cup 2022?', 'Messi', 'Mbappe', 'Ronaldo', 'Giroud', 2)," +
                "('Cầu thủ nào có nhiều Quả bóng vàng nhất?', 'Ronaldo', 'Messi', 'Modric', 'Ronaldinho', 2)," +
                "('CLB nào vô địch Champions League 2023?', 'Real Madrid', 'Man City', 'Liverpool', 'PSG', 2)," +
                "('Sân Camp Nou thuộc đội nào?', 'Barcelona', 'Real Madrid', 'Valencia', 'Sevilla', 1)," +
                "('Ai là thủ môn xuất sắc nhất World Cup 2022?', 'Lloris', 'Emiliano Martinez', 'Courtois', 'Neuer', 2)," +
                "('Ai là vua phá lưới Premier League 2023?', 'Haaland', 'Kane', 'Salah', 'Son Heung-min', 1)," +
                "('Cầu thủ nào là đội trưởng của Argentina tại World Cup 2022?', 'Messi', 'Di Maria', 'Martinez', 'De Paul', 1)," +
                "('Quốc gia nào tổ chức World Cup 2018?', 'Nga', 'Pháp', 'Mỹ', 'Brazil', 1)," +
                "('Đội nào có biệt danh Quỷ đỏ nước Anh?', 'Arsenal', 'Manchester United', 'Liverpool', 'Chelsea', 2)," +
                "('Ai là HLV của Man City 2024?', 'Pep Guardiola', 'Mikel Arteta', 'Jurgen Klopp', 'Ten Hag', 1)," +
                "('Đội bóng nào có biệt danh The Gunners?', 'Chelsea', 'Liverpool', 'Arsenal', 'Tottenham', 3)," +
                "('Ai là cầu thủ ghi nhiều bàn nhất lịch sử Real Madrid?', 'Benzema', 'Cristiano Ronaldo', 'Raul', 'Zidane', 2)," +
                "('Ai giành Champions League đầu tiên của PSG?', 'Chưa từng', '2020', '2018', '2021', 1)," +
                "('Cầu thủ nào có nickname El Fenomeno?', 'Ronaldinho', 'Ronaldo Nazario', 'Romario', 'Adriano', 2)," +
                "('Cầu thủ nào từng khoác áo cả MU và Man City?', 'Tevez', 'Schmeichel', 'Cole', 'Evans', 1)," +
                "('Ai là cầu thủ đắt giá nhất lịch sử Real Madrid?', 'Hazard', 'Bellingham', 'Bale', 'Cristiano Ronaldo', 1)," +
                "('Quốc gia nào có nhiều chức vô địch World Cup nhất?', 'Argentina', 'Brazil', 'Pháp', 'Đức', 2)," +
                "('Ai là vua phá lưới Euro 2020?', 'Kane', 'Cristiano Ronaldo', 'Lukaku', 'Mbappe', 2)," +
                "('Ai là thủ môn giữ sạch lưới nhiều nhất World Cup?', 'Neuer', 'Casillas', 'Buffon', 'Taffarel', 1)," +
                "('Đội bóng nào vô địch Ngoại hạng Anh 2023?', 'Arsenal', 'Liverpool', 'Man City', 'Chelsea', 3)," +
                "('Ai là cầu thủ châu Á đầu tiên giành danh hiệu Vua phá lưới Premier League?', 'Son Heung-min', 'Okazaki', 'Kagawa', 'Park Ji-sung', 1)," +
                "('Ai là HLV huyền thoại của MU?', 'Mourinho', 'Alex Ferguson', 'David Moyes', 'Louis Van Gaal', 2)," +
                "('Cầu thủ nào có biệt danh King Eric?', 'Cantona', 'Beckham', 'Giggs', 'Rooney', 1)," +
                "('Ai là đội trưởng của Liverpool năm 2023?', 'Van Dijk', 'Salah', 'Henderson', 'Alisson', 3)," +
                "('Sân vận động lớn nhất thế giới là?', 'Camp Nou', 'Wembley', 'Rungrado 1st of May', 'Maracana', 3)," +
                "('Ai là cầu thủ đầu tiên đạt 1000 bàn thắng?', 'Pelé', 'Maradona', 'Messi', 'Cristiano Ronaldo', 1)," +
                "('Đội nào là chủ nhà World Cup 2002?', 'Nhật Bản & Hàn Quốc', 'Mỹ', 'Pháp', 'Tây Ban Nha', 1)," +
                "('Ai là cầu thủ đầu tiên giành 5 Quả bóng vàng?', 'Messi', 'Ronaldo', 'Cruyff', 'Platini', 2)," +
                "('Ai là cầu thủ có số áo 7 huyền thoại ở MU?', 'Beckham', 'Ronaldo', 'Cantona', 'Cả 3', 4)," +
                "('Ai là cầu thủ chạy nhanh nhất thế giới 2023?', 'Mbappe', 'Alphonso Davies', 'Haaland', 'Vinicius', 1)," +
                "('Ai là cầu thủ Việt Nam đầu tiên sang châu Âu thi đấu?', 'Công Phượng', 'Văn Hậu', 'Quang Hải', 'Lương Xuân Trường', 3)," +
                "('Cầu thủ nào ghi bàn nhiều nhất lịch sử CLB Barcelona?', 'Suarez', 'Messi', 'Ronaldinho', 'Eto’o', 2)," +
                "('Đội nào có logo hình con gà trống?', 'Tottenham', 'Arsenal', 'Chelsea', 'Leeds', 1)," +
                "('Đội bóng nào vô địch World Cup 2018?', 'Pháp', 'Croatia', 'Anh', 'Bỉ', 1)," +
                "('Ai là thủ môn xuất sắc nhất World Cup 2018?', 'Courtois', 'Lloris', 'De Gea', 'Neuer', 1)," +
                "('Cầu thủ nào giữ kỷ lục ra sân nhiều nhất cho Real Madrid?', 'Raul', 'Casillas', 'Ramos', 'Benzema', 3)," +
                "('Đội tuyển nào có biệt danh “La Roja”?', 'Tây Ban Nha', 'Chile', 'Bồ Đào Nha', 'Thụy Sĩ', 1)," +
                "('Cầu thủ nào ghi nhiều bàn nhất lịch sử Premier League?', 'Harry Kane', 'Alan Shearer', 'Rooney', 'Aguero', 2)," +
                "('Ai là đội trưởng của Argentina vô địch World Cup 2022?', 'Messi', 'Di Maria', 'Otamendi', 'De Paul', 1)," +
                "('Đội bóng nào có biệt danh “The Red Devils”?', 'Manchester United', 'Liverpool', 'Arsenal', 'Chelsea', 1)," +
                "('Ai là cầu thủ ghi bàn trong cả 5 kỳ World Cup?', 'Messi', 'Ronaldo', 'Pele', 'Maradona', 2)," +
                "('Đội nào vô địch Champions League 2020?', 'Bayern Munich', 'PSG', 'Real Madrid', 'Chelsea', 1)," +
                "('Cầu thủ nào ghi bàn nhanh nhất lịch sử World Cup?', 'Hakan Şükür', 'Mbappe', 'Ronaldo', 'Eusebio', 1)," +
                "('Ai là cầu thủ đầu tiên đạt 100 bàn tại Champions League?', 'Ronaldo', 'Messi', 'Lewandowski', 'Benzema', 1)," +
                "('Ai là HLV dẫn dắt Manchester City vô địch Champions League đầu tiên?', 'Guardiola', 'Mancini', 'Pellegrini', 'Arteta', 1)," +
                "('Đội nào giành cú ăn ba mùa 1998–1999?', 'Manchester United', 'Real Madrid', 'Barcelona', 'AC Milan', 1)," +
                "('Ai là cầu thủ Việt Nam giành Quả bóng vàng 2023?', 'Quang Hải', 'Hoàng Đức', 'Công Phượng', 'Tiến Linh', 2)," +
                "('Đội bóng nào có sân nhà Camp Nou?', 'Real Madrid', 'Barcelona', 'Atletico Madrid', 'Valencia', 2)," +
                "('Cầu thủ nào có biệt danh “El Fenomeno”?', 'Ronaldo Nazario', 'Ronaldinho', 'Romario', 'Rivaldo', 1)," +
                "('Đội tuyển nào có nhiều danh hiệu Copa America nhất?', 'Argentina', 'Uruguay', 'Brazil', 'Chile', 2)," +
                "('Cầu thủ nào ghi bàn trong trận chung kết Euro 2016?', 'Eder', 'Ronaldo', 'Griezmann', 'Pogba', 1)," +
                "('Ai là cầu thủ trẻ nhất từng ghi bàn ở World Cup?', 'Pele', 'Mbappe', 'Messi', 'Ronaldo', 1)," +
                "('Đội tuyển nào là chủ nhà World Cup 2022?', 'Qatar', 'Nga', 'Brazil', 'Nam Phi', 1)," +
                "('Ai là thủ môn giữ sạch lưới nhiều nhất lịch sử Premier League?', 'Cech', 'De Gea', 'Schmeichel', 'Alisson', 1)," +
                "('Đội bóng nào có biệt danh “The Blues”?', 'Chelsea', 'Leicester', 'Everton', 'Tottenham', 1)," +
                "('Ai là cầu thủ ghi bàn quyết định giúp Italia vô địch Euro 2020?', 'Donnarumma', 'Chiellini', 'Jorginho', 'Saka', 4)," +
                "('Cầu thủ nào ghi bàn trong trận chung kết Champions League 2023?', 'Rodri', 'Haaland', 'Mahrez', 'Grealish', 1)," +
                "('Đội nào vô địch AFF Cup 2018?', 'Việt Nam', 'Thái Lan', 'Malaysia', 'Indonesia', 1)," +
                "('Ai là đội trưởng của tuyển Pháp tại World Cup 2022?', 'Lloris', 'Griezmann', 'Mbappe', 'Varane', 1)," +
                "('Đội tuyển nào có biệt danh “Azzurri”?', 'Ý', 'Pháp', 'Bỉ', 'Hà Lan', 1)," +
                "('Cầu thủ nào là người ghi bàn duy nhất trong chung kết World Cup 2010?', 'Iniesta', 'Torres', 'Villa', 'Xavi', 1)," +
                "('Ai là HLV của Real Madrid năm 2024?', 'Ancelotti', 'Zidane', 'Mourinho', 'Tuchel', 1)");
    }

    // 📋 Lấy toàn bộ câu hỏi
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Question(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    // 🎲 Lấy ngẫu nhiên N câu hỏi
    public ArrayList<Question> getRandomQuestions(int limit) {
        ArrayList<Question> all = getAllQuestions();
        Collections.shuffle(all);
        return new ArrayList<>(all.subList(0, Math.min(limit, all.size())));
    }

    // 💾 Lưu điểm người chơi
    public void saveScore(String playerName, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("playerName", playerName);
        values.put("score", score);
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    // 🏆 Lấy top 5 người chơi có điểm cao nhất
    public ArrayList<String> getTopScores() {
        ArrayList<String> topList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT playerName, score FROM " + TABLE_SCORES + " ORDER BY score DESC LIMIT 5", null);

        if (cursor.moveToFirst()) {
            do {
                topList.add(cursor.getString(0) + " — " + cursor.getInt(1) + " điểm");
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return topList;
    }
}
