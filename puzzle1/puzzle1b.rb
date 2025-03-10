require 'ruby-nfc'
require 'logger'

class Rfid
  def initialize	
	@logger = Logger.new(STDOUT)
	@logger.level = Logger::INFO
  end

def read_uid
	NFC::Reader.all.each do |reader|
	reader.poll(Mifare::Classic::Tag) do |tag|

	if tag.class == (Mifare::Classic::Tag)
		uid = tag.uid.unpack1('H*').upcase[0,8]

		return uid
	end

       end
      end 
    end
end

if __FILE__ == $0
	rf = Rfid.new
	uid = rf.read_uid
	puts "UID llegit: #{uid}" if uid
end
