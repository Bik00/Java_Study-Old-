package project1107_2;

//�ǸŴ� ������ �ϴ� buffer Ŭ���� �ۼ�
//����ȭ ��� ���� get, put�� sync
class Buffer {
	// ���� ������ �����ϱ� ���� ��� ����
	int data;
	// ����ִ��� üũ�ϱ� ���� ����
	boolean empty;

	// ������
	public Buffer() {
		empty = true;
		data = -99;
	}

	// ����ȭ ��� ���� get, put�� synchronized
	public synchronized int get() {
		// ���ۿ� �����Ͱ� ������ wait �ؾ� �Ѵ�.
		while (empty) { // ���ۿ� �����Ͱ� ������
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���⿡ �����ϸ� empty�� false��� �̾߱� : �� ������ �ִ�.
		empty = true;
		System.out.println("�Һ��� " + data + "��° ���� �Һ�");
		notifyAll(); // �����ڸ� ������
		return data;
	}

	public synchronized void put(int inputData) {
		// ���ۿ� �����Ͱ� ������ wait �ؾ� �Ѵ�.
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���⿡ �����ϸ� empty�� true��� �̾߱� : �� ������ �����.
		empty = false;
		data = inputData;
		System.out.println("������ " + inputData + "��° ���� ����");
		notifyAll(); // �����ڸ� ������
		return;
	}
}

// ������ Ŭ������ ������
class Producer extends Thread {
	// ��� ����
	Buffer myBuffer;

	// ������
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

// �Һ��� Ŭ������ ������
class Consumer implements Runnable {
	// ��� ����
	Buffer myBuffer;

	// ������
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
		// 1. ���� ��ü ����
		// 2. ������ ��ü ������ ���� ��ü�� �Ű�������
		// 3. �Һ��� ��ü ������ ���� ��ü�� �Ű�������
		// 4. ������ ��ü Thread ����
		// 5. �Һ��� ��ü Thread ����
		// 1. ���� ��ü ����
		Buffer mBuffer = new Buffer();
		// 2. ������ ��ü ������ ���� ��ü�� �Ű�������
		Producer p1 = new Producer(mBuffer);
		// 3. �Һ��� ��ü ������ ���� ��ü�� �Ű�������
		Consumer c1 = new Consumer(mBuffer);
		// 4. ������ ��ü Thread ���� (Thread Ŭ������ ���� ���)
		p1.start();
		// 5. �Һ��� ��ü Thread ���� ( Runnable �������̽��κ��� ���)
		Thread tc1 = new Thread(c1);
		tc1.start();
	}

}