package com.example.MyBookShopApp.config;

import com.example.MyBookShopApp.data.TestEntity;

import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.TestEntityCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class CommandLineRunnerImpl implements CommandLineRunner {
    TestEntityCrudRepository testEntityCrudRepository;
    BookRepository bookRepository;
    @Autowired
    public CommandLineRunnerImpl(TestEntityCrudRepository testEntityCrudRepository, BookRepository bookRepository) {
        this.testEntityCrudRepository = testEntityCrudRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 100; i++) {
            createTestEntity(new TestEntity());
        }

        TestEntity readTestEntity = readTestEntityById(3L);
        if(readTestEntity != null){
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("read " + readTestEntity.toString());
        }else {
            throw new NullPointerException();
        }

        TestEntity updatedTestEntity = updateTestEntity(5L);
        if(updatedTestEntity != null){
            Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName()).info("update"+updatedTestEntity.toString());
        }else {
            throw new NullPointerException();
        }

        deleteTestEntityById(4L);
        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName())
                .info(bookRepository.findBookByAuthor_FirstName("Hazlett").toString());
        Logger.getLogger(CommandLineRunnerImpl.class.getSimpleName())
                .info(bookRepository.customFindAllBooks().toString());
    }

    private void deleteTestEntityById(Long id)
    {
        TestEntity testEntity = testEntityCrudRepository.findById(id).get();
        testEntityCrudRepository.delete(testEntity);

    }

    private TestEntity updateTestEntity(Long id) {
      TestEntity testEntity = testEntityCrudRepository.findById(id).get();
      testEntity.setData("New Data");
      testEntityCrudRepository.save(testEntity);
      return testEntity;
    }

    private TestEntity readTestEntityById(Long id) {
      return testEntityCrudRepository.findById(id).get();
    }

    private void createTestEntity(TestEntity entity) {
        entity.setData(entity.getClass().getSimpleName()+entity.hashCode());
        testEntityCrudRepository.save(entity);
    }
}
