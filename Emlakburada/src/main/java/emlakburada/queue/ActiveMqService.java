package emlakburada.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import emlakburada.config.JmsConfig;
import emlakburada.service.EmailMessage;

@Service
public class ActiveMqService implements QueueService{
	@Autowired
    private JmsTemplate jmsTemplate;
	
	@Autowired
	private JmsConfig config;

	@Override
    public void sendMessage(EmailMessage eMail) {
        try {
            jmsTemplate.convertAndSend(config.getQueueName(), eMail.getEmail());
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

}
