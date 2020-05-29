import lombok.extern.slf4j.Slf4j;
import model.Box;
import org.junit.Test;
import service.StudentSerivce;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lqq
 * @date 2020/3/23
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        //Unsafe unsafe = Unsafe.getUnsafe(); //throwed java.lang.SecurityException: Unsafe
        //getDeclaredField获取所有的字段 getField获取public的字段
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

//        long idOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("id"));
//        long nameOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("name"));
//
//        User user = new User();
//        unsafe.compareAndSwapInt(user,idOffset,0,10);
//        unsafe.compareAndSwapObject(user,nameOffset,null,"user1");
//        log.info("user={}",user);


    }


    @Test
    public void test0() throws InterruptedException {

        System.setProperty("java.io.tmpdir", "D:/AppTemp");
        System.out.println(System.getProperty("java.io.tmpdir"));
//
//        for (int i = 0; i < 5; i++) {
//            new Thread(()->{
//                ThreadLocalRandom random = ThreadLocalRandom.current();
//                List<Integer> codes = new ArrayList<>();
//                for (int j = 0; j < 5; j++) {
//                    codes.add(10000+random.nextInt(89999));
//
//                }
//                log.info("codes={}",codes);
//            },"thread_"+i).start();
//        }
        TimeUnit.SECONDS.sleep(100000);


    }


    @Test
    public void test1() throws InterruptedException {
//        Thread t1 = new Thread(() -> {
//            log.info("1");

//            long time = TimeUnit.NANOSECONDS.convert(2, TimeUnit.SECONDS);
//            LockSupport.parkNanos(time);
//            log.info("2");
//        }, "t1");
//        t1.start();
//
//        TimeUnit.SECONDS.sleep(3);
        //LockSupport.unpark(t1);
    }


    @Test
    public void test2() throws Exception {
        ReentrantLock lock = new ReentrantLock(true);

        new Thread(() -> {
            lock.lock();
            try {
                log.info("test1");
                TimeUnit.DAYS.sleep(20);
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "thread1").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            lock.lock();
            try {
                log.info("test2");
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "thread2").start();


        TimeUnit.SECONDS.sleep(1);

        new Thread(() -> {
            lock.lock();
            try {
                log.info("test3");
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "thread3").start();

        System.in.read();

    }


    @Test
    public void test() throws InterruptedException {
        new Thread(() -> {
            log.info("t1");
            Thread.currentThread().interrupt();
            log.info("t1");
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);
    }


    @Test
    public void test3() throws InterruptedException, IOException {
        ReentrantLock lock = new ReentrantLock();

        Condition condition = lock.newCondition();
        AtomicInteger state = new AtomicInteger(0);

        new Thread(() -> {
            lock.lock();
            try {
                log.info("before await");
                state.incrementAndGet();
                condition.await();
                log.info("after await");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            lock.lock();
            try {
                state.incrementAndGet();


                log.info("before await");
                condition.await();
                log.info("after await");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();

        TimeUnit.SECONDS.sleep(1);
        lock.lock();
        try {

            log.info("before signal");
            condition.signal();
            log.info("after signal");
        } finally {
            lock.unlock();
        }

        TimeUnit.HOURS.sleep(1);
    }


    static final int SHARED_SHIFT = 16;
    static final int SHARED_UNIT = (1 << SHARED_SHIFT);
    static final int MAX_COUNT = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    @Test
    public void test4() throws InterruptedException {
        ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rw.readLock();
        readLock.lock();
        readLock.unlock();
    }


    @Test
    public void test5() {

        //如果StringTable已有,intern则直接返回已有的字面值
        //如果StringTable没有,intern则以x.字面值为key，以x的引用为value存储到StringTable中，并返回value 1.7及以后
        //如果StringTable没有,intern则以x.字面值为key，以对x的引用的复制后的引用为value存储到StringTable中，并返回value 1.6
        String x = new String(new char[]{'a', 'b', 'c'});
        String s = x.intern();
        String a = "abc";
        log.info("a==x:{}", a == x);
        log.info("a==s:{}", a == s);
        log.info("s==x:{}", s == x);


    }


    @Test
    public void test6() {
        Class<StudentSerivce> clazz = StudentSerivce.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            log.info("name={} returnType={}", method.getName(), method.getReturnType());
        }
    }


    @Test
    public void test7() {
        Box<Integer, String> box = new Box<>(1, "black");

        log.info("box={}", box);


        //Class<Object> clazz0 = getSuperClassGenricType(box.getClass(), 0);
        //Class<Object> clazz1 = getSuperClassGenricType(box.getClass(), 1);

        Class<?> clazz0 = box.getTClassType();
        Class<?> clazz1 = box.getEClassType();
        log.info("0={},1={}", clazz0, clazz1);


    }


}


