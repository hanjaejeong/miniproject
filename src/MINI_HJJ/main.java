package MINI_HJJ;


import java.util.Scanner;

//뷰
public class main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		System.out.println("============영어 단어 프로그램============");
		controller control = new controller();
		model md = new model();

		while (true) {
			System.out.println("===1.회원가입 2.로그인 3.아이디삭제 4.프로그램종료===");
			System.out.print("메뉴 선택: ");
			int menu = sc.nextInt();

			switch (menu) {
			case 1: // 회원가입

				control.creatid();

				break;
			case 2:
				control.loginmain();

				break;
			case 3:// 아이디 삭제

				control.deletemain();

				break;
			case 4:// 종료
				System.out.println("게임을 종료합니다.");
				break;
			default:
				System.out.println("잘못된 입력");
				break;
			}

			if (menu == 4) {
				break;
			}

		}

	}

}