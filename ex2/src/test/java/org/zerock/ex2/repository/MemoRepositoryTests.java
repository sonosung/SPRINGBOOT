package org.zerock.ex2.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.ex2.entity.Memo;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository; //Main 폴더에서 생성한, JpaRepository를 상속받는 MemoRepository를
                                   //test 폴더에서 사용하기 위해 @Autowried를 사용하여 자동적으로 연결시킨다.
                                   //MemoRepository는 인터페이스 그 자체가 되고,

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());

        }
    @Test
    public void testInsertDummies() { //한번 실행할때마다 1~100번째 데이터를 입력함. 만약 이미 100개가 존재하면, 101번부터 200번까지 입력함.

        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo); //insert 를 위한 메소드.
        });
    }

    @Test
    public void testSelect(){
        //데이버베이스에 존재하는 mno
        Long mno = 100L; //DB에서100번째 행의 Long type 데이터 값을 변수로 선언.

        Optional<Memo> result = memoRepository.findById(mno); //select를 위한 메소드.

        System.out.println("===============================");

        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {

        //데이터베이스에 존재하는 mno
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("================================");

        System.out.println(memo);
    }

    @Test
    public void testUpdate() {

        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {

        //1페이지 당 10개의 코드를 할당.
        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("----------------------------------");

        System.out.println("Total Pages : " + result.getTotalPages());

        System.out.println("Total Count : " + result.getTotalElements());

        System.out.println("Page Number : " + result.getNumber());

        System.out.println("Page Size : " + result.getSize());

        System.out.println("has next page? : " + result.hasNext());

        System.out.println("first page? : " + result.isFirst());

        System.out.println("----------------------------------");

        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {

        Sort sort1 = Sort.by("mno").descending();
       // Sort sort2 = Sort.by("memoText").ascending();
        //Sort sortAll = sort1.and(sort2); //and 를 이용해서 sort1 과 sort2 를 연결!

        Pageable pageable = PageRequest.of(0, 10, sort1);

       // Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOderByMnoDesc(70L,80L);

        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
