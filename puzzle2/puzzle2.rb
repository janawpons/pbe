require 'gtk3'
require_relative 'puzzle1b'

	app = Gtk::Application.new("org.gtk.example", :flags_none)

	rf = Rfid.new  # classe del puzzle1b

	app.signal_connect "activate" do |application|
  window = Gtk::ApplicationWindow.new(application)
 	window.set_title("rfid_gtk")
  window.set_default_size(200, 150)
  window.set_border_width(10)
	
	css_provider = Gtk::CssProvider.new
	css_provider.load_from_path(File.expand_path("style.css", __dir__))
	Gtk::StyleContext.add_provider_for_screen(Gdk::Screen.default, css_provider, Gtk::StyleProvider::PRIORITY_USER)

	label = Gtk::Label.new()
  enunciat = Gtk::Box.new(:vertical, 10)
	enunciat.pack_start(label, expand: true, fill: true, padding: 5)
  button = Gtk::Button.new(label: "Clear")
  enunciat.pack_start(button, expand: false, fill: true, padding: 5)
	
 	window.add(enunciat)
    
  def llegir_targeta (label, rf, window)
      label.set_text("Siusplau, identifica't amb el teu carnet de la universitat:")
      window.style_context.add_class("reading") #lila

      Thread.new do
	    uid = rf.read_uid
          
  	    GLib::Idle.add do
  	     label.set_text("UID llegit: #{uid}")
          window.style_context.remove_class("reading")
          window.style_context.add_class("success")
  	    false
        end
      end
   end

	llegir_targeta(label,rf,window)
	
  button.signal_connect "clicked" do
    llegir_targeta(label,rf,window)
  end
  window.show_all
end
puts app.run([$0] + ARGV)
