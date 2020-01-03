/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sit.int303.grianisland.game.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sit.int303.grianisland.game.model.MapManager;
import sit.int303.grianisland.core.model.Player;
import sit.int303.grianisland.game.model.Card;
import sit.int303.grianisland.game.model.CardManager;
import sit.int303.grianisland.game.model.Npc;
import sit.int303.grianisland.game.model.quest.Quest;
import sit.int303.grianisland.game.model.quest.QuestMessage;

/**
 *
 * @author Varavut
 */
@WebServlet(name="TalkNpc", urlPatterns={"/TalkNpc"})
public class TalkNpc extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //response.setContentType("text/xml;charset=UTF-8");
        MapManager maps=(MapManager) getServletContext().getAttribute("maps");
        Player player=(Player) request.getSession(false).getAttribute("player");
        int mapId = player.getMap();
        String npcIdString = request.getParameter("npc");
        String questIdString = request.getParameter("quest");
        String messageIdString = request.getParameter("message");
        String choiceIdString = request.getParameter("choice");
        String queryString = request.getParameter("query");
        int npcId=Integer.parseInt(npcIdString);
        int questId = -1;
        Npc npc=maps.getMap(mapId).getNpc(npcId);
        System.out.print("test1");
       
        if(questIdString !=null && !questIdString.equals(""))
        {
            //ถ้ามี questid ส่งมา
            questId=Integer.parseInt(questIdString);
            Quest quest=npc.getQuest(questId);
            int messageId=Integer.parseInt(messageIdString);
            QuestMessage message = quest.getQuestMessage(messageId);
            //System.out.print("test2");
            int choiceId = 0;
            if(choiceIdString!=null)
            {
                //ถ้าส่ง choiceId มา
                choiceId=Integer.parseInt(choiceIdString);
                if(queryString==null)
                    queryString=",";
                queryString+=choiceId+",";
            }
            if(!message.isQuestion())
            {
                QuestMessage nextMessage = quest.getQuestMessage(message.getNextMessage());
                if(message.getNextMessage()==-2)
                {
                    System.out.println("query : "+queryString);
                    System.out.println("trueQuery : "+quest.getTrueChoiceString());
                    //ถ้าไม่มีข้อความถัดไป
                    if(queryString.equals(quest.getTrueChoiceString()))
                    {
                        try {
                            if(quest.getReward()!=0)
                            {
                                CardManager cm = new CardManager(player.getId());
                                cm.addCard(quest.getReward());
                                request.getSession().setAttribute("cardset", cm.loadCard());
                                request.setAttribute("reward", quest.getReward());
                                System.out.println("win");
                                player.addQuest(quest.getQuestId());
                            }
                            request.setAttribute("message", quest.getQuestMessage(message.getQuestMessageId() + 1));
                        } catch (SQLException ex) {
                            System.out.println(ex);
                        } catch (ClassNotFoundException ex) {
                            System.out.println(ex);
                        }
                    }
                    else
                    {
                        System.out.println("lose");
                        request.setAttribute("message",quest.getQuestMessage(message.getQuestMessageId()+2));
                    }
                }
                else
                {
                    //มี message ถัดไป
                    request.setAttribute("message",nextMessage);

                    //System.out.print("test3");
                }
                if(message.getNextMessage()==-1)
                    return;
            }
            else
            {
                //System.out.print(message.getQuestChoice(choiceId).getNextMessage());
                request.setAttribute("message",quest.getQuestMessage(message.getQuestChoice(choiceId).getNextMessage()));
            }
        }
        else
        {
            try {
                player.loadQuests();
                LinkedList<Integer> playerQuests = player.getQuests();
                TreeMap<Integer, Quest> quests = npc.getQuests();
                Set<Integer> questsId = quests.keySet();
                for (int qId : questsId) {
                    if (!playerQuests.contains(qId)) {
                        questId = qId;
                        break;
                    }
                }
                if (questId != -1) {
                    TreeMap<Integer, QuestMessage> questMessages = npc.getQuest(questId).getQuestMessages();
                    Collection<QuestMessage> values = questMessages.values();
                    TreeSet<QuestMessage> valueSet = new TreeSet<QuestMessage>();
                    valueSet.addAll(values);
                    for (QuestMessage qm : valueSet) {
                        request.setAttribute("message", qm);
                        break;
                    }
                }
                queryString = ",";
            } catch (SQLException ex) {
                Logger.getLogger(TalkNpc.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TalkNpc.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        QuestMessage qm=(QuestMessage) request.getAttribute("message");
        //System.out.print(qm.getMessage());
        //System.out.print(qm.getQuestMessageId());

        request.setAttribute("query", queryString);
        request.setAttribute("questid", questId);
        request.setAttribute("npc", npc.getNpcId());
        getServletContext().getRequestDispatcher("/Quest.jsp").forward(request, response);
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
