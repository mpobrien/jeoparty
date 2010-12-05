from jparty.models import *
#from jparty.models.dao import *
from jparty.ctrlrs import HomeController
from jparty.modules import *
from com.mob.web import AppConfigModule
from java.io import File
from com.google.inject import *
modules = [AppConfigModule(File("conf/settings.properties"), [HomeController.getPackage()]),
           MongoModule(),
           MainServletModule() ] 
injector = Guice.createInjector(modules)
