package ro.ubbcluj.map.pb3.repository.file;

import ro.ubbcluj.map.pb3.domain.Entity;
import ro.ubbcluj.map.pb3.domain.validators.Validator;

import ro.ubbcluj.map.pb3.repository.memory.InMemoryRepository;


import java.io.*;

import java.util.Arrays;
import java.util.List;


/**
 *
 * @param <ID> - id
 * @param <E> - entity
 */
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {

    String fileName;

    /**
     *
     * @param fileName - filename
     * @param validator - validators
     */
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null && !line.equals("")) {
                List<String> atributes = Arrays.asList(line.split(";"));
//                System.out.println(atributes);
                E e = extractEntity(atributes);
                super.save(e);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @param attributes - read atributes from file
     * @return an entity of type E
     */
    protected abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    /**
     *
     * @param entity - entity
     * @return - entity in string format
     */
    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity){
        if (super.save(entity) != null){
            return entity;
        }
        writeToFile(entity);
        return null;
    }

    @Override
    public E update(E entity) {
        if (super.update(entity) != null) {
            return  entity;
        }
        writeToFile();
        return null;
    }

    @Override
    public E delete(ID id) {
        E entity = super.delete(id);
        if(entity != null) {
            writeToFile();
            return null;
        }
        return entity;
    }


    /**
     *
     * @param entity - entity
     */
    protected void writeToFile(E entity){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(createEntityAsString(entity));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * for update
     */
    protected void writeToFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) {
            Iterable<E> all = super.findAll();
            for (E curent : all) {
                bw.write(createEntityAsString(curent));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







}

