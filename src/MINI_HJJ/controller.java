package MINI_HJJ;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class controller {
   private String url = "jdbc:oracle:thin:@localhost:1521:xe";
   private String user = "madang";
   private String pass = "madang";

   private Connection con = null;
   private PreparedStatement pst = null;
   private ResultSet rs = null;

   private boolean result = false;
   private String logId = null;
   private String word = null;
   private String mean = null;
   private boolean resultPlus = false;
   private int score = 0;

   Scanner sc = new Scanner(System.in);

   model md = new model();

   public void connect() {
      // jdbc 드라이버 동적로딩
      // 데이터베이스 연결
      try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
         con = DriverManager.getConnection(url, user, pass);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void close(ResultSet rs) {
      try {
         rs.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   public void close(PreparedStatement pst) {
      if (pst != null) {
         try {
            pst.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }

   public void close(Connection con) {
      if (con != null) {
         try {
            con.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }

   public void creatid() {

      int regcounter = 0;
      System.out.println("======1.회원가입======");

      while (regcounter != 2) {// 회원가입 반복체크
         if (regcounter == 1) {
            System.out.println();
            System.out.println("아이디와 패스워드를 정확히 입력해주세요.");
            System.out.println();
         }

         // sc.nextLine();// 입력시 기존 문자열 초기화
         do {
            System.out.print("아이디(공백없이 입력해주세요): ");
            md.setLogId(sc.nextLine());

            System.out.println("입력받은 아이디: " + md.getLogId());

         } while (emptyid(md.getLogId()));

         do {
            System.out.print("패스워드(공백없이 숫자만 입력해주세요): ");
            md.setTestnum(sc.nextLine());
         } while (numtest(md.getTestnum()));

         md.setLogPass(Integer.parseInt(md.getTestnum())); // 패스워드에 입력받은 문자 숫자로

         try {
            // 아이디 비번 체크 메서드 (1 성공 0 비밀번호 틀림 -1아이디 없음)
            if (userCheck(md.getLogId(), md.getLogPass()) == 1) {
               System.out.println("같은 아이디가 존재합니다.");
               regcounter++;
            } else if (userCheck(md.getLogId(), md.getLogPass()) == 0) {
               System.out.println("같은 아이디가 존재합니다.");
               regcounter++;
            } else if (userCheck(md.getLogId(), md.getLogPass()) == -1) {
               result = insertStd(md.getLogId(), md.getLogPass());
               regcounter = 2;
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

      }

      if (result == true) {
         System.out.println("회원가입 성공");
      } else {
         System.out.println("회원가입 실패");
      }

   }

   public void loginmain() {
      System.out.println("======2.로그인======");
      // sc.nextLine();// 입력시 기존 문자열 초기화
      do {
         System.out.print("아이디(공백없이 입력해주세요): ");

         md.setLogId(sc.nextLine());
         System.out.println("입력받은 아이디 : " + md.getLogId());

      } while (emptyid(md.getLogId()));
      do {
         System.out.print("패스워드(공백없이 숫자만 입력해주세요): ");
         // logPass = sc.nextInt();
         md.setTestnum(sc.nextLine());
      } while (numtest(md.getTestnum()));
      md.setLogPass(Integer.parseInt(md.getTestnum()));

      // 수정필요

      try {
         // 아이디 비번 체크 메서드 (1 성공 0 비밀번호 틀림 -1아이디 없음)
         if (userCheck(md.getLogId(), md.getLogPass()) == 1) {// 로그인성공
            // 로그인성공
            System.out.println("======로그인 성공======");
            System.out.println();
            while (true) {
               
               md.setScore(0);
               
            
               System.out.println("===1.게임시작 2.오답노트 3.랭킹 4.프로그램종료===");
               System.out.print("메뉴선택: ");

               Scanner sc = new Scanner(System.in);
               int menu = sc.nextInt();
               sc.nextLine();
               switch (menu) {
               case 1:

                  while (true) {
                     System.out.print("난이도를 상, 중, 하 중 선택하시오: ");
                     String levelSelect = sc.nextLine();
                     if (levelSelect.equals("상")) {
                        game(md.getLogId(), "상");
                        break;
                     } else if (levelSelect.equals("중")) {
                        game(md.getLogId(), "중");
                        break;
                     } else if (levelSelect.equals("하")) {
                        game(md.getLogId(), "하");
                        break;
                     } else {
                        System.out.println("잘못된 입력입니다.");
                     }
                  }
                  break;
               case 2:
                  System.out.println("======오답노트======");
                  ArrayList<model> wrongnote = selectStd(md.getLogId());
                  for (int i = 0; i < wrongnote.size(); i++) {
                     md = wrongnote.get(i);
                     System.out.print(md.getWord() + ": ");
                     System.out.print(md.getMean());
                     System.out.println();
                  }
                  System.out.println();
                  break;
               case 3:
                  System.out.println("======랭킹======");
                  ArrayList<model> rank = rankStd(md.getLogId());
                  for (int i = 0; i < rank.size(); i++) {
                     System.out.println((i + 1) + "등 ");
                     md = rank.get(i);
                     System.out.print("아이디: " + md.getLogId() + "/");
                     System.out.print("점수: " + md.getScore());
                     System.out.println();
                  }

                  break;
               case 4:
                  System.out.println("게임을 종료합니다.");
                  System.exit(0);
                  break;
               }
            }

         } else if (userCheck(md.getLogId(), md.getLogPass()) == 0) {
            System.out.println("비밀번호 오류");

         } else if (userCheck(md.getLogId(), md.getLogPass()) == -1) {
            System.out.println("아이디가 없습니다.");
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   public void deletemain() {
      System.out.println("======3. 아이디 삭제======");
      // sc.nextLine();// 입력시 기존 문자열 초기화
      do {
         System.out.print("아이디(공백없이 입력해주세요): ");
         md.setLogId(sc.nextLine());
         System.out.println("입력받은 아이디 : " + md.getLogId());
      } while (emptyid(md.getLogId()));

      do {
         System.out.print("패스워드(공백없이 숫자만 입력해주세요): ");
         md.setTestnum(sc.nextLine());
      } while (numtest(md.getTestnum()));

      md.setLogPass(Integer.parseInt(md.getTestnum())); // 패스워드에 입력받은 문자 숫자로

      deletID(md.getLogId(), md.getLogPass());
   }

   public boolean insertStd(String name, int age) { // 아이디 입력 메소드, namer
      try {
         connect();// 드라이버 동적로딩
         // sql 작성
         String sql = "INSERT INTO \"MADANG\".\"LOGINSERVER\" (LOGID, LOGPASS) VALUES(?, ?) ";
         // sql문 설명
         // madang 데이터베이스의 loginserver 테이블중 logid와 logpass 값에 뒤에 올 1번 2번값을 넣는다

         pst = con.prepareStatement(sql); // 동적변수 입력하기 위해 prepareStatement 클래스 소환
         pst.setString(1, name);// 첫번재 물음표에 name값을 넣는다
         pst.setInt(2, age);// 두번째 물음표에 age 값을 넣는다

         // sql 실행처리
         int cnt = pst.executeUpdate(); // select문을 제외한 쿼리문이 실행되면 점수를 출력한다,
         // crate,drop은 -1을, INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드의 건수를 반환합니다
         if (cnt > 0) {// 쿼리문이 실행되면 점수가 올라감
            result = true; // 쿼리문 실행시 트루값 반환
         } else {
            result = false;//
         }

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         close(pst); // 쿼리사용을위해 불러온 함수 종료
         close(con);
      }

      return result;
   }

   public int userCheck(String id, int passwd) throws Exception { // 아이디, 비번 중복체크 메서드

      String sql = "";
      String dbpasswd = "";
      int x = -1;

      try {
         connect();
         // con = DriverManager.getConnection(url, user, pass); // db연결

         sql = "select logpass from loginserver where logid = ?";
         // sql문으로 loginserver 테이블의 logid 값과 같은 logpass 값을 가져온다(비번 검색)

         pst = con.prepareStatement(sql);
         pst.setString(1, id); // 쿼리문중에 ?값에 아이디를 집어 넣는다.
         rs = pst.executeQuery(); // 쿼리문 출력값을 자바로 읽어들이기 위해 사용한다.

         if (rs.next()) {// 출력값을 읽어들이려면 rx.next를 필수로 써야한다. 출력값이 있을경우(패스워드가 존재할경우)
            dbpasswd = rs.getString("logpass"); // 쿼리 출력결과중 logpass 열에 있는 패스워드를 변수에 대입한다.
            int a = Integer.parseInt(dbpasswd); // 스트링형을 인트로 변환(dppasswd는 string 형 이므로), 변수 a에 대입한다.
            if (a == passwd) // 입력했던 패스워드가 테이블에 있는 패스워드와 일치하면
               x = 1; // 인증성공
            else
               x = 0; // 비밀번호 틀림
         } else
            x = -1; // logid가 테이블에 없을경우(아이디 존재x) 해당 아이디 없음
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         close(pst);
         close(con);
      }
      return x;
   }

   public void deletID(String id, int passwd) {
      try {
         if (userCheck(id, passwd) == 1) { // 아이디 비번 체크 메서드 (1 성공 0 비밀번호 틀림 -1아이디 없음)
            // 만약 아이디, 패스워드가 모두 일치하면
            connect();
            String sql = "delete from loginserver where logId =?"; // loginserver 에서 logid와 일치한 데이터 삭제
            // 4. 바인드변수 채워기
            pst = con.prepareStatement(sql);
            pst.setString(1, id);

            // 5. sql 실행처리
            // excuteUpdate => insert, update, delete
            int cnt = pst.executeUpdate(); // 쿼리 수행 결과값 체크
            if (cnt > 0) {
               System.out.println("아이디 삭제 성공");
            } else {
               System.out.println("아이디 삭제 실패");
            }
         } else if (userCheck(id, passwd) == 0) {// 아이디는 일치하지만 비번이 틀리면
            System.out.println("비밀번호가 틀립니다. ");
         } else {// 아이디가 없어서 -1값이 올경우
            System.out.println("아이디가 없습니다");
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         close(pst);
         close(con);
      }
   }

   public boolean emptyid(String str) { // 문자열 공백 체크메서드
      @SuppressWarnings("unused")
      boolean result = false;

      if (str.indexOf(" ") != -1) {// indexOf함수는 문자열을 검색해 일치하는 값을 출력한다.
         // -1로 하면 찾는문자열이 0일경우가 된다. 즉 공백이 존재하지 않는 경우가 아닐경우(!=연산자 이므로 )= 공백이 있을경우.

         return true; // 띄어쓰기가 있으면 true값 반환
      }

      return str == null || str.isEmpty();// 3항 연상자를 이용한다
      // 1. null값일경우
      // 2, isEmpty = 입력값이 빈칸일경우
      // 3. 두경우 하나라도 참일경우 true값을 str변수에 집어넣고 반환한다(true값)

   }

   // 패스워드 숫자확인
   public boolean numtest(String test) {
      final String REGEX = "[0-9]+";// 정규식 이용(정규식은 일정한 규칙을가진 문자열) 변수가 변하지 않게 상수 final로 선언
      // [0-9] 뜻: 입력값이 0-9일경우
      // + 뜻 : 앞의 문자가 하나, 그이상
      // 즉 [0-9]+는 0123456789의 숫자 모음이다. 정규식은 숫자,문자의 모음을 뜻한다. 0123456789가 아니다. 0부터 9까지의
      // 모음이다.

      if (test.matches(REGEX)) { // 입력받은 수가 string형이므로 입력받은test값과 정규식 regex값과 비교를 위해 matches함수를 이용해야한다.
         // 비교식 할때 둘다string 형이면 ==로 비교가 되지 않는다. (string 은 참조형이므로)

         // System.out.println("숫자만 있습니다.");//테스트용 출력문, 숫자만 있으면 테스트문 출력됨

         return false;// test로 받은 값이 1234567890로만 이루어 질경우 false 값 반환
      } else {
         System.out.println("숫자외에 값이 존재합니다.");
         return true;
      }

   }

   public int game(String id, String level) { // 게임 실행 메서드, id값과 난이도 상,중,하를 가져왔다.

      int result = 0;
      String tempword = null;
      @SuppressWarnings("unused")
      int error = 0;
      int tempscore = md.getScore();// 점수
      int multiple = 0;// 난이도 점수 배수, 7 5 3
      int multiminus = 0;// 난이도 오답 배수, 5,3,1점
      switch (level) {
      case "상":
         multiple = 7; // 점수는 7
         multiminus = 5;// 오답배수는 5
         break;
      case "중":
         multiple = 5;
         multiminus = 3;
         break;
      case "하":
         multiple = 3;
         multiminus = 1;
         break;
      }
      System.out.println("**게임이 시작됩니다. EXIT를 입력하면 게임이 종료됩니다.");
      System.out.println("**오답 15회 입력시 결과가 출력됩니다.");

      Random rand = new Random(); // 랜덤함수를 사용하기위해 선언한다.
      int rd = rand.nextInt(3000); // 랜덤함수의 nextInt()의 가로안의 값은 최대수를 의미한다
      // 즉 0부터 3000사이의 int수를 랜덤으로 뽑아서 rd값에 집어 넣는다.

      try {
         connect();
         // con = DriverManager.getConnection(url, user, pass);

         for (int gamecount = 0; gamecount < 16;) {
            String sql = "select word, mean from ( select * from wordserver where levels =? order by DBMS_RANDOM.RANDOM ) where rownum <2";
            // sql 난이도 구분하고 랜덤정렬후 하나 뽑아온다
            /*
             * sql문 설명: select word, mean //3.뽑아온 값중에 word, mean (단어와 뜻)을 뽑아온다 from ( select
             * * //1.wordserver 테이블의 level 열과 일치하는값을 랜덤하게 뽑아온다 from wordserver where levels
             * =? //?값에 상,중,하를 넣어서 일치하는 값을 뽑는다 order by DBMS_RANDOM.RANDOM ) where rownum
             * <2뽑아온 값 중에 2이하, 즉 하나만 가져온다. 랜덤으로 뽑아올대 rownum을 사용한다.
             */

            pst = con.prepareStatement(sql);
            pst.setString(1, level); // sql문 중에 ?에 level값, 즉 main 클래스에서 받아온 상, 중, 하 를 집어 넣는다.
            ResultSet rs = pst.executeQuery(); // 쿼리문 결과 뽑아오기
            while (rs.next()) {// 쿼리문 결과를 자바에서 쓰기위해 필수
               md.setWord(rs.getString("word")); // word 열의 데이터를 model클래스의 word변수에 집어넣기
               md.setMean(rs.getString("mean"));// mean 열의 데이터를 model클래스의 mean변수에 집어넣기
            }
            System.out.println("다음의 단어에 알맞는 뜻을 입력하세요: " + md.getWord()); // 받아온 영어
            System.out.print("===> ");
            tempword = sc.nextLine(); // 입력값 tempword에 입력

            if (md.getMean().equals(tempword)) {// tempword값과 받아온 영어뜻이 일치하면
               System.out.println("정답! 점수가 " + multiple + "점 올라갑니다.");
               tempscore += multiple; // 점수 추가, 난이도 에 따라 다른 점수 더해준다.
               md.setScore(tempscore);// 임시 점수를 model클래스의 점수에 대입
               System.out.println("현재 첨수 : " + tempscore + "점"); // 플러스된 점수 출력

            } else if (tempword.equals("exit")) {// 입력값이 exit를 받으면
               System.out.println("점수를 서버에 업데이트중...");
                updateScore(md.getScore(), md.getLogId());
               System.out.println("게임을 종료합니다.");
              
               
               System.out.println();
               break;
               // System.exit(0);// 게임종료
            } else {// 입력값이 정답이 아니고, exit가 아닐경우
               System.out.println("오답입니다. 정답은 " + md.getMean() + " 입니다."); // 정답을 출력해준다
               tempscore -= multiminus;// 오답이므로 현재 점수에 난이도에 따라 점수를 감소시킨다.
               md.setScore(tempscore); // 감소시킨 점수를 model클래스의 점수에 집어 넣는다

               System.out.println("감점 : -" + multiminus + ", 현재 점수는 : " + md.getScore() + " 입니다.");// 감점,점수 출력
               gamecount++;// 오답이므로 오답회수를 1회 증가
               System.out.println("현재 오답 횟수 : " + gamecount);// 오답 회수 출력
               // 오답
               setResultPlus(insertWord(md.getLogId(), md.getWord(), md.getMean()));
               if (gamecount == 15) {// 오답횟수가 15가 되면
                  System.out.println("오답회수 초과로 게임을 종료합니다.");
                  System.out.println("게임 점수 : " + md.getScore()); // 게임점수 출력
                  System.out.println();

                  updateScore(tempscore, md.getLogId());
                  break;
                  // System.exit(0);
               }
            }

            // 점수가 100점 이상일때 게임 종료
            if (tempscore >= 100) {
               System.out.println("현재 점수가 100점 이상입니다. 게임을 종료합니다.");
               System.out.println();
               updateScore(tempscore, md.getLogId());
               break;
               // System.exit(0);
            }
         }

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         close(pst);
         close(con);
      }
      return result;
   }

   // 오답노트 저장
   public boolean insertWord(String logId, String word, String mean) {
      try {
         connect();
         String sql = "insert into noteserver values(?, ?, ?)";
         pst = con.prepareStatement(sql);
         pst.setString(1, logId);
         pst.setString(2, word);
         pst.setString(3, mean);

         int cnt = pst.executeUpdate();
         if (cnt > 0) {
            result = true;
         } else {
            result = false;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return result;
   }

   public ArrayList<model> selectStd(String logId) {
      ArrayList<model> wrongnote = new ArrayList<model>();
      try {
         connect();
         // 3. sql 작성
         String sql = " select * from noteserver where logId = ? order by logId desc ";
         pst = con.prepareStatement(sql);
         pst.setString(1, logId);
         // 4. sql 실행처리
         rs = pst.executeQuery();
         while (rs.next()) {
            logId = rs.getString("logId");
            word = rs.getString("word");
            mean = rs.getString("mean");
            md = new model(logId, word, mean);
            wrongnote.add(md);
         }

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         close(pst);
         close(con);
      }
      return wrongnote;
   }

   public void updateScore(int tempscore, String logId) {
      try {
         connect();

         String sql = "INSERT INTO \"MADANG\".\"GAMESERVER\" (myRank,miscnt,LOGID, SCORE) VALUES(0,0,?, ?)";
         pst = con.prepareStatement(sql);
         pst.setString(1, logId);
         pst.setInt(2, tempscore);

         pst.executeUpdate();

      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public ArrayList<model> rankStd(String logId) {
      ArrayList<model> rank = new ArrayList<model>();
      try {
         connect();
         // 3. sql 작성
         String sql = " select * from gameserver order by score desc ";
         pst = con.prepareStatement(sql);
         // 4. sql 실행처리
         rs = pst.executeQuery();
         while (rs.next()) {
            logId = rs.getString("logId");
            score = rs.getInt("score");
            md = new model(logId, score);
            rank.add(md);
         }

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         close(pst);
         close(con);
      }
      return rank;
   }

   public boolean isResultPlus() {
      return resultPlus;
   }

   public void setResultPlus(boolean resultPlus) {
      this.resultPlus = resultPlus;
   }

   public String getLogId() {
      return logId;
   }

   public void setLogId(String logId) {
      this.logId = logId;
   }
}