package JavaNIO.SharedMemory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ReadShareMemory {
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("/Users/adrian/Documents/IdeaProjects/thinkingInSpring/InterviewPreparation/src/main/java/JavaNIO/SharedMemory/swap.mm", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

        int lastIndex = 0;

        for (int i = 1; i < 27; i++) {
            int flag = mbb.get(0);
            int index = mbb.get(1);

            if (flag != 2 || index == lastIndex) {
                i--;
                continue;
            }

            lastIndex = index;
            System.out.println("程序 ReadShareMemory: " + System.currentTimeMillis() + ": 位置： " + index + " 读出数据" + (char)mbb.get(index));

            mbb.put(0, (byte) 0);

            if (index == 27) {
                break;
            }
        }

    }
}
