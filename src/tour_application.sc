require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: patterns.sc
  
theme: /Application
    
    state: Appl_form
        a: Ответьте на пару вопросов, чтобы я мог сформировать заявку для Вас и отправить ее нашему менеджеру. Вы готовы?
        state: Yes
            q: *$yes*
            go:/Application /Query
            
            
        state: No
            q: *$no*
            a: Чем я могу Вам помочь?
            
    state: Query
        
