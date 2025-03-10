# ACR122U USB reader
# ruby versió 3.3.5
# instal·lar smartcard abans

require 'smartcard' # llibreria smartcard

class Rfid
    def initialize
        @context = Smartcard::PCSC::Context.new # inicialitza el context Smartcard
        @reader = @context.readers.first # obté el primer lector disponible
    end

    def wait_for_card
        queries = Smartcard::PCSC::ReaderStateQueries.new(1)
        queries[0].current_state = :empty # indica que no hi ha cap targeta present
        queries[0].reader_name = @reader
        
        @context.wait_for_status_change(queries) # espera fins que es detecti canvi d'estat
        queries.ack_changes
    end

    def read_uid
        wait_for_card
        begin
            targeta = @context.card(@reader, :shared) # connecta amb la targeta
            raise "No s'ha pogut connectar amb la targeta" unless targeta
            
            resposta = targeta.transmit("\xFF\xCA\x00\x00\x04") #llegeix uid de la targeta
            
            uid = resposta.unpack('H*').first.upcase # conversió a hexadecimal i majúscules
            
            targeta.transmit "\xFF\x00\x40\xCF\x04\x03\x00\x01\x01" rescue nil #per a desactivar el buzzer del lector
           
            uid
            rescue StandardError => e
                    puts "Error: #{e.message}"
                    nil
        end
    end
end

if __FILE__ == $0
    rf = Rfid.new
    uid = rf.read_uid
    puts "UID llegit: #{uid}" if uid
end
