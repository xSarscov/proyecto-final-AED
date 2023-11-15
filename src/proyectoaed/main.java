/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proyectoaed;

import model.ModelUser;
import componentes.*;
import connection.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JLayeredPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.*;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.SwingUtilities;
import model.ModelLogin;
import model.ModelMessage;
import model.ModelReset;
import service.*;

/**
 *
 * @author Ernesto
 */
public class main extends javax.swing.JFrame {

    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading loading;
    private PanelVerifyCode verifyCode;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final DecimalFormat df = new DecimalFormat("##0.###");
    private final double loginSize = 60;
    private ServiceUser service;
    private PanelPasswordRecovery passwordRecovery;
    private PanelPasswordCode passwordCode;
    private PanelChangePassword change;
    private PanelResetPassword reset;
    private ModelReset recoveryUser;

    public main() {
        initComponents();
        init();
    }

    private void init() {
        passwordRecovery = new PanelPasswordRecovery();
        service = new ServiceUser();
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        verifyCode = new PanelVerifyCode();
        passwordCode = new PanelPasswordCode();
        reset = new PanelResetPassword();
        change = new PanelChangePassword();
        ActionListener eventRegister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        };

        ActionListener eventLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        };

        ActionListener eventPassRecovery = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                passwordRecovery.setVisible(true);
                            }
                        });
                    }
                }).start();

            }
        };

        loginAndRegister = new PanelLoginAndRegister(eventRegister, eventLogin, eventPassRecovery);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {

                double fractionCover;
                double size = coverSize;
                double fractionLogin;

                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }

                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;

                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;

                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }

                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }

                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };

        Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
        bg.setLayer(passwordRecovery, JLayeredPane.POPUP_LAYER);
        bg.setLayer(passwordCode, JLayeredPane.POPUP_LAYER);
        bg.setLayer(reset, JLayeredPane.POPUP_LAYER);
        bg.setLayer(change, JLayeredPane.POPUP_LAYER);
        bg.add(loading, "pos 0 0 100% 100%");
        bg.add(verifyCode, "pos 0 0 100% 100%");
        bg.add(passwordRecovery, "pos 0 0 100% 100%");
        bg.add(passwordCode, "pos 0 0 100% 100%");
        bg.add(reset, "pos 0 0 100% 100%");
        bg.add(change, "pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%");
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
        verifyCode.addEventButtonVerify(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ModelUser user = loginAndRegister.getUser();
                    if (service.verifyCodeWithUser(user.getUserID(), verifyCode.getInputCode())) {
                        service.doneVerify(user.getUserID());
                        showMessage(Message.MessageType.SUCCESS, "Registrado correctamente");
                        verifyCode.setVisible(false);
                        MainSystem.main(user);
                    } else {
                        showMessage(Message.MessageType.ERROR, "Código de verificación incorrecto");
                    }
                } catch (SQLException e) {
                    showMessage(Message.MessageType.ERROR, "Error");
                }
            }
        });
        passwordRecovery.addEventButtonRecovery(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                passwordRecovery();
            }
        });

        passwordCode.addEventButtonVerify(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (service.verifyRecoveryCode(passwordCode.getInputCode())) {
                        service.doneAuth(recoveryUser.getUserID());
                        showMessage(Message.MessageType.SUCCESS, "Verificado correctamente");
                        passwordCode.setVisible(false);
                        change.setVisible(true);
                    } else {
                        showMessage(Message.MessageType.ERROR, "Verificación fallida");
                    }
                } catch (SQLException er) {
                    showMessage(Message.MessageType.ERROR, "Error");
                }
            }
        });
        change.addEventButtonReset(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loading.setVisible(true);
                String newPassword = change.getInputPass1();
                String confirmPassword = change.getInputPass2();

                try {
                    if (service.checkDuplicatePassword(newPassword)) {
                        loading.setVisible(false);
                        showMessage(Message.MessageType.ERROR, "Tu nueva contraseña debe ser diferente de la última que usaste");
                    } else if (!newPassword.equals(confirmPassword)) {
                        loading.setVisible(false);
                        showMessage(Message.MessageType.ERROR, "Las contraseñas no coinciden");
                    } else {
                        service.doneReset(recoveryUser.getUserID(), newPassword);
                        loading.setVisible(false);
                        change.setVisible(false);
                        reset.setVisible(true);

                    }

                } catch (SQLException error) {
                    loading.setVisible(false);
                    showMessage(Message.MessageType.ERROR, "Error");
                }

            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 933, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        try {
            DatabaseConnection.getInstance().connectToDatabase();
            System.out.println("Conexión a la base de datos satisfactoria");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new main().setVisible(true);
            }
        });
    }

    private void passwordRecovery() {

        String email = passwordRecovery.getInputEmail();
        System.out.println(email);
        try {
            recoveryUser = service.getUserByEmail(email);
            if (service.checkEmail(recoveryUser.getEmail()) && recoveryUser != null) {
                recoveryUser.setVerifyCode(service.setRecoveryCode(email));
                sendPasswordRecovery(recoveryUser);
            } else if (email.isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Por favor, completa todos los campos obligatorios para continuar.");
            } else if (!isValidEmail(email)) {
                showMessage(Message.MessageType.ERROR, "Por favor, ingresa una dirección de correo electrónico válida.");
            } else {
                showMessage(Message.MessageType.ERROR, "El correo ingresado no está asociado a una cuenta");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
            showMessage(Message.MessageType.ERROR, "Error");
        }
    }

    public void register() {
        ModelUser user = loginAndRegister.getUser();
        try {
            if (service.checkDuplicateUser(user.getUserName())) {
                showMessage(Message.MessageType.ERROR, "El nombre de usuario ya existe.");
            } else if (service.checkDuplicateEmail(user.getEmail())) {
                showMessage(Message.MessageType.ERROR, "El correo electrónico ya está vinculado a una cuenta.");

            } else if (user.getUserName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
                showMessage(Message.MessageType.ERROR, "Por favor, completa todos los campos obligatorios para continuar.");
            } else if (!isValidEmail(user.getEmail())) {
                showMessage(Message.MessageType.ERROR, "Por favor, ingresa una dirección de correo electrónico válida.");
            } else {
                service.insertUser(user);
                sendMain(user);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessage(Message.MessageType.ERROR, "Error al registrar.");
        }
    }

    private boolean isValidEmail(String email) {
        // Expresión regular para validar direcciones de correo electrónico
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Crea un objeto Pattern a partir de la expresión regular
        Pattern pattern = Pattern.compile(emailRegex);

        // Comprueba si la dirección de correo electrónico coincide con la expresión regular
        if (email == null) {
            return false;  // La dirección de correo electrónico es nula, no es válida
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void login() {
        ModelLogin data = loginAndRegister.getDataLogin();
        try {
            ModelUser user = service.login(data);
            if (user != null) {
                this.dispose();
                MainSystem.main(user);
            } else {
                showMessage(Message.MessageType.ERROR, "El correo o la contraseña es incorrecta");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showMessage(Message.MessageType.ERROR, "Error al iniciar sesión.");
        }
    }

    private void sendMain(ModelUser user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loading.setVisible(true);
                ModelMessage ms = new ServiceMail().sendMain(user.getEmail(), user.getVerifyCode());
                if (ms.isSuccess()) {
                    loading.setVisible(false);
                    verifyCode.setVisible(true);
                } else {
                    loading.setVisible(false);
                    showMessage(Message.MessageType.ERROR, ms.getMessage());
                }
            }
        }).start();
    }

    private void sendPasswordRecovery(ModelReset user) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                loading.setVisible(true);
                ModelMessage ms = new ServiceMail().sendMain(user.getEmail(), user.getVerifyCode());
                System.out.println("Code: " + user.getVerifyCode());

                if (ms.isSuccess()) {
                    loading.setVisible(false);
                    passwordRecovery.setVisible(false);
                    passwordCode.setVisible(true);

                } else {
                    loading.setVisible(false);
                    showMessage(Message.MessageType.ERROR, ms.getMessage());
                }
            }
        }).start();
    }

    private void showMessage(Message.MessageType messageType, String message) {
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!ms.isShow()) {
                    bg.add(ms, "pos 0.5al -30", 0); //  Insert to bg fist index 0
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (ms.isShow()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = 40 * fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if (ms.isShow()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                } else {
                    ms.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    animator.start();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
