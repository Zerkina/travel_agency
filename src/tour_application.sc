require: slotfilling/slotFilling.sc
  module = sys.zb-common
require: patterns.sc
  
theme: /Application
    
    state: Appl_form
        q!: *([оформит*] заявк* [на]  [тур])*
        a: Ответьте на пару вопросов, чтобы я мог сформировать заявку и отправить ее нашему менеджеру. Вы готовы?
        state: Yes
            q: * $yes *
            go!:/Application/Query
            
            
        state: No
            q: * $no *
            go!:/General/Start
            
            
    state: Query
        a: Молодец
        
