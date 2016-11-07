package project1107;
//�ǸŴ� ������ �ϴ� buffer Ŭ���� �ۼ�
//����ȭ ��� ���� get, put�� sync	
class Buffer {
	//���� ������ �����ϱ� ���� �ɹ� ����
	int data[];
	//����ִ��� äũ�ϱ� ���� ����
	int count;
	
	//������
	public Buffer() {
		data = new int[5];
		count = -1;
	}
	//�ǸŴ� ������ �ϴ� buffer Ŭ���� �ۼ�
	//����ȭ ��� ���� get, put�� sync	
	public synchronized int[] get() {
		//���ۿ� �����Ͱ� ������ wait �ؾ� �Ѵ�.
		while(count==-1) { //���ۿ� �����Ͱ� ������
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//���⿡ �����ϸ� empty�� false��� �̾߱� : �� ������ �ִ�.
		for(int i=0;i<5;i++) {
			
		};
		count++;
		notifyAll(); //�����ڸ� ������
		return data[];
	}
	
	public synchronized void put(int inputData) {
		//���ۿ� �����Ͱ� ������ wait �ؾ� �Ѵ�.

		
		while (!empty) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//���⿡ �����ϸ� empty�� true��� �̾߱� : �� ������ �����.
		
		empty = false;
		data = inputData;
		count++;
		notifyAll();
		return;
	}
}
//������ Ŭ������ ������
class Producer extends Thread 
{
	//�ɹ� ����
	Buffer myBuffer;
	//������
	public Producer(Buffer inputBuffer) {
		myBuffer = inputBuffer;
	}
	@Override
	public void run() {
		for(int i = 0;i<100;i++) {
			myBuffer.put(i);
			
		}
		
		
	}
}
//�Һ��� Ŭ������ ������
class Consumer implements Runnable {
	//�ɹ� ����
	Buffer myBuffer;
	
	//������
	public Consumer(Buffer inputBuffer) {
		myBuffer = inputBuffer;
	}
	
	@Override
	public void run() {
		for(int i=0;i<100;i++) {
			int k = myBuffer.get();
			System.out.println("�Һ��� "+k+"��° ���� �Һ�");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class WDB {

	public static void main(String[] args) {
		//1. ���� ��ü ����
		//2. ������ ��ü ������ ���� ��ü�� �Ű�������
		//3. �Һ��� ��ü ������ ���� ��ü�� �Ű�������
		//4. ������ ��ü Thread ����
		//5. �Һ��� ��ü Thread ����
		
		//1. ���� ��ü ����
		Buffer mBuffer = new Buffer();
		//2. ������ ��ü ������ ���� ��ü�� �Ű�������
		Producer p1 = new Producer(mBuffer);
		//3. �Һ��� ��ü ���� �� ���� ��ü�� �Ű�������
		Consumer c1 = new Consumer(mBuffer);
		//4. ������ ��ü Thread ���� (Thread Ŭ�����κ��� ���)
		p1.start();
		//5. �Һ��� ��ü Thread ���� (Runnable �������̽��κ��� ���)
		Thread tc1 = new Thread(c1);
		tc1.start();
	}

}