package com.capgemini.capa.service;

import com.pff.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

/**
 * Service for reading PST Files.
 *
 */
@Service
public class PSTService {

    private final Logger log = LoggerFactory.getLogger(PSTService.class);
    private int depth = -1;

    public PSTService() {
    }

    public void getAppointment(){
        try {
            PSTFile file = new PSTFile("C:\\Users\\alvogel\\Documents\\Development\\Repository\\capa\\backup.pst");
            System.out.println(file.getMessageStore().getDisplayName());
            //processFolder(file.getRootFolder());
            //processFolder(calendar);
            PSTFolder folder = getCalendarFolder(file.getRootFolder());
            assert folder != null;
        } catch (PSTException | IOException e) {
            e.printStackTrace();

        }
    }

    private PSTFolder getCalendarFolder(PSTFolder rootFolder) throws PSTException, IOException {
        depth++;
        PSTFolder calendar = rootFolder;
        if (depth > 0) {
            System.out.println(rootFolder.getDisplayName());
        }
        if (Objects.equals(rootFolder.getDisplayName(), "Calendar")) {
            calendar = rootFolder;
            String containerClass = rootFolder.getContainerClass();
            if ("IPF.Appointment".equals(containerClass)) {
                System.out.println(rootFolder.getDisplayName() + " [" + rootFolder.getContainerClass() + "]");
                PSTAppointment appointment = (PSTAppointment)rootFolder.getNextChild();
                while (appointment != null) {
                    print(appointment);
                    appointment = (PSTAppointment) rootFolder.getNextChild();
                }
            }
        } else {
            if (rootFolder.hasSubfolders()) {
                Vector<PSTFolder> childFolders = rootFolder.getSubFolders();
                for (PSTFolder childFolder : childFolders) {
                   getCalendarFolder(childFolder);
                }
            }
        }
        return calendar;
    }

//    private void processFolder(PSTFolder folder)
//        throws PSTException, java.io.IOException
//    {
//        // go through the folders...
//        if (folder.hasSubfolders()) {
//            Vector<PSTFolder> childFolders = folder.getSubFolders();
//            for (PSTFolder childFolder : childFolders) {
//                processFolder(childFolder);
//            }
//        }
//
//        String containerClass = folder.getContainerClass();
//        if ("IPF.Appointment".equals(containerClass)) {
//            System.out.println(folder.getDisplayName() + " [" + folder.getContainerClass() + "]");
//
//            PSTAppointment appointment = (PSTAppointment)folder.getNextChild();
//            while (appointment != null) {
//                print(appointment);
//                appointment = (PSTAppointment) folder.getNextChild();
//            }
//        }
//    }

    private void print(PSTAppointment appointment) {
        System.out.println(appointment.getSubject());
        System.out.println("\t" + appointment.getCreationTime());
        System.out.println("\t" + appointment.getAllAttendees());
        System.out.println("\t" + appointment.getMessageClass());
    }

}
