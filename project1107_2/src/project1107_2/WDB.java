package project1107_2;

//판매대 역할을 하는 buffer 클래스 작성
//동기화 제어를 위해 get, put은 sync
class Buffer {
	// 피자 순서를 저장하기 위한 멤버 변수
	int data;
	// 비어있는지 체크하기 위한 변수
	boolean empty;

	// 생성자
	public Buffer() {
		empty = true;
		data = -99;
	}

	// 동기화 제어를 위해 get, put은 synchronized
	public synchronized int get() {
		// 버퍼에 데이터가 없으면 wait 해야 한다.
		while (empty) { // 버퍼에 데이터가 없으면
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 여기에 도달하면 empty가 false라는 이야기 : 즉 물건이 있다.
		empty = true;
		System.out.println("소비자 " + data + "번째 피자 소비");
		notifyAll(); // 생산자를 깨워라
		return data;
	}

	public synchronized void put(int inputData) {
		// 버퍼에 데이터가 있으면 wait 해야 한다.
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 여기에 도달하면 empty가 true라는 이야기 : 즉 물건이 비었다.
		empty = false;
		data = inputData;
		System.out.println("생산자 " + inputData + "번째 피자 생산");
		notifyAll(); // 생산자를 깨워라
		return;
	}
}

// 생산자 클래스를 만들자
class Producer extends Thread {
	// 멤버 변수
	Buffer myBuffer;

	// 생성자
	public Producer(Buffer inputBuffer) {
		myBuffer = inputBuffer;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			myBuffer.put(i);
		}
	}
}

// 소비자 클래스를 만들자
class Consumer implements Runnable {
	// 멤버 변수
	Buffer myBuffer;

	// 생성자
	public Consumer(Buffer inputBuffer) {
		myBuffer = inputBuffer;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			int k = myBuffer.get();
		}
	}

}

public class WDB {

	public static void main(String[] args) {
		// 1. 버퍼 객체 생성
		// 2. 생산자 객체 생성시 버퍼 객체를 매개변수로
		// 3. 소비자 객체 생성시 버퍼 객체를 매개변수로
		// 4. 생산자 객체 Thread 실행
		// 5. 소비자 객체 Thread 실행
		// 1. 버퍼 객체 생성
		Buffer mBuffer = new Buffer();
		// 2. 생산자 객체 생성시 버퍼 객체를 매개변수로
		Producer p1 = new Producer(mBuffer);
		// 3. 소비자 객체 생성시 버퍼 객체를 매개변수로
		Consumer c1 = new Consumer(mBuffer);
		// 4. 생산자 객체 Thread 실행 (Thread 클래스로 부터 상속)
		p1.start();
		// 5. 소비자 객체 Thread 실행 ( Runnable 인터페이스로부터 상속)
		Thread tc1 = new Thread(c1);
		tc1.start();
	}

}