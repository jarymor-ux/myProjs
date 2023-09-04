import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import lombok.Getter;


public class XMLHandler extends DefaultHandler
{
   
    private Voter voter;

   
    private int batchSize = 128;
    private ArrayList<Voter> batch = new ArrayList<>();

    private final static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private final static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    @Getter
    private HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();

    @Getter
    private HashMap<Voter, Integer> voterCounts = new HashMap<>();


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException
    {
        if (qName.equals("voter"))
        {
            try
            {
                String name = attributes.getValue("name");
                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
                voter = new Voter(name, birthDay);

                Integer count = voterCounts.get(voter);
                voterCounts.put(voter, count == null ? 1 : count + 1);

                batch.add(voter);
            }
            catch (ParseException e)
            {
                voter = null;
            }
        }
        if (qName.equals("visit") && voter != null)
        {
            try
            {
                Integer station = Integer.parseInt(attributes.getValue("station"));
                Date time = visitDateFormat.parse(attributes.getValue("time"));

                WorkTime workTime = voteStationWorkTimes.get(station);
                if (workTime == null)
                {
                    workTime = new WorkTime();
                    voteStationWorkTimes.put(station, workTime);
                }
                workTime.addVisitTime(time.getTime());
            }
            catch (ParseException e)
            {
            }
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException
    {
        if (qName.equals("voter"))
        {
            voter = null;

            if (batch.size() >= batchSize)
            {
                try
                {
                    DBConnection.multiInsertVotersCount(batch);
                    batch.clear();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }


    @Override
    public void endDocument()
    {
        
        if (batch.size() >= 0)
        {
            try
            {
                DBConnection.multiInsertVotersCount(batch);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }


    public void printWorkTimes()
    {
        System.out.println("Voting station work times: ");
        for (Integer votingStation : voteStationWorkTimes.keySet())
        {
            WorkTime workTime = voteStationWorkTimes.get(votingStation);
            System.out.println("\t" + votingStation + " - " + workTime);
        }
    }


    public void printDuplicatedVoters()
    {
        System.out.println("Duplicated voters: ");
        for (Voter voter : voterCounts.keySet())
        {
            Integer count = voterCounts.get(voter);
            if (count > 1)
            {
                System.out.println("\t" + voter + " - " + count);
            }
        }
    }

}
