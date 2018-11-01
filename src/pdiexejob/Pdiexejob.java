/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//adding  comment
package pdiexejob;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

/**
 *
 * @author Kunal Patil
 */
public class Pdiexejob {

    /**
     * @param args the command line arguments
     * @throws org.pentaho.di.core.exception.KettleException
     */
    public static void main(String[] args) throws KettleException, ClassNotFoundException {
        // TODO code application logic here
        
        KettleEnvironment.init(false);
        EnvUtil.environmentInit();
        KettleDatabaseRepository repository = new KettleDatabaseRepository();
        Class.forName("oracle.jdbc.driver.OracleDriver");
        DatabaseMeta databaseMeta = new DatabaseMeta("Oracle", "Oracle", "", "localhost", "xe", "1521", "EWSETL", "EWSETL");
        KettleDatabaseRepositoryMeta kettleDatabaseMeta = new KettleDatabaseRepositoryMeta("EWSETLtest", "EWSETLtest", "Transformation description", databaseMeta);
        repository.init(kettleDatabaseMeta);
        repository.connect("admin", "admin");
        RepositoryDirectoryInterface directory = repository.loadRepositoryDirectoryTree();
        
        TransMeta transformationMeta = repository.loadTransformation("testjobexec",directory,null,true,null);
        Trans trans = new Trans(transformationMeta);
        //trans.setParameterValue();
        trans.execute(null);
        trans.waitUntilFinished();
        if(trans.getErrors() > 0){
            System.out.println("Errors");
        }
        else{
            System.out.println("hurrah !! NO Errors");
        }
    }

}
