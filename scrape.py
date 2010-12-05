#!/usr/bin/python
from BeautifulSoup import BeautifulSoup, NavigableString, BeautifulStoneSoup
from htmlentitydefs import name2codepoint as n2cp
import HTMLParser
import MySQLdb
import os, sys
import re
import re
import urllib
import urllib2

dollaramount = re.compile("[^\d]")

URL = "http://www.j-archive.com/showgame.php"
#     html = gethtml(173)
#     print html
poop = None

try:
    conn = MySQLdb.connect(host="localhost", 
                           user="root", passwd="turtl3",
                           db="jeopardy",
                           use_unicode=True)
    cursor = conn.cursor()
except MySQLdb.Error, e:
    print "Error %d: %s" % (e.args[0], e.args[1])
    sys.exit (1)

def gethtml(game_id):#{{{
    URL_id = URL + "?game_id=" + str(game_id)
    req = urllib2.Request(URL_id)
    response = urllib2.urlopen(req)
    html = response.read();
    response.close()
    return html#}}}

def testfile():#{{{
    tf = open(os.getcwd() + '/' + "testfile2.html", 'r')
    contents = tf.read()
    tf.close()
    return contents#}}}

def getquestions(soupobjs, game_id):#{{{
    global poop
    ts = soupobjs.html.findAll('table',attrs={'class':'round'}, text=False)
    categorylist = []
    for t in ts:
        for c in t.contents:
            if isinstance(c,NavigableString): continue
            #print type(c) == type(NavigableString)
            categories = c.findAll('td',attrs={'class':'category_name'})
            if not categories: continue
            for cat in categories:
                #catname = BeautifulStoneSoup(cat.string, BeautifulSoup.HTML_ENTITIES).contents[0]
                catname = u''.join([q.string for q in cat.findAll(text=True)])
                categorylist.append( Category(catname, game_id) )
    for round in [(0,'J'),(1,'DJ')]:
        for i in xrange(1,7):
            for j in xrange(1,6):
                question = soupobjs.html.findAll('td',id='clue_' + round[1] + '_' +str(i)+'_' + str(j))
                if not question: continue
                toptag = question[0].parent.parent;
                value = toptag.findAll(lambda tag: tag.name=='td' and tag.get('class','').startswith('clue_value'))[0].string
                answer = toptag.findAll('div')[0]['onmouseover']
                if answer:
                    answerobj = BeautifulSoup(answer).findAll(attrs={'class':'correct_response'})[0]
                    answertxt = decode_htmlentities(u''.join([q.string for q in answerobj.findAll(text=True)]) )
                questiontxt= decode_htmlentities(u''.join([q.string for q in question[0].findAll(text=True)]))
                links = question[0].findAll("a")
                newQuestion = QuestionAnswer(value, questiontxt, answertxt )
                categorylist[i-1 + 6 * round[0]].addquestion(newQuestion)
                if links:
                    for l in links:
                        newQuestion.addurl( l['href'] )
    return categorylist#}}}

class QuestionAnswer:#{{{
    def __init__(self, value, question, answer ):#{{{
        self.urls = []
        try:
            if value.find('DD') >= 0: 
                self.value = 0
            else: 
                self.value = int( re.sub(dollaramount, '', value).strip() )
        except Exception, e:
            print e
            self.value = -1
        self.question = unicode( question )
        self.answer = unicode( answer )#}}}

    def addurl(self, url):#{{{
        self.urls.append(url)#}}}

    def __str__(self):#{{{
        result = "("+ str(self.value) + ")"
        result += self.question + "\n\t"
        result += self.answer
        return result
        #}}}#}}}

    def store(self, categoryid):
        #try:
        cursor.execute("INSERT into question (category_id, value, question, answer)" + 
                          " values (%s, %s, %s, %s)", 
                           (categoryid, self.value, 
                           self.question.encode('UTF-8'),
                           self.answer.encode('UTF-8')))
        #except:
            #print categoryid, self.value, self.question, self.answer
        self.dbid = cursor.lastrowid
        if self.urls:
            for url in self.urls:
                cursor.execute("INSERT into urls (question_id, url)" + 
                              " values (%s, %s)", 
                               (self.dbid, url))

class Category:#{{{
    def __init__(self, name, game_id):#{{{
        self.name = unicode(name)
        self.questions = []
        self.game_id = game_id #}}}

    def addquestion(self, question):#{{{
        self.questions.append(question)#}}}

    def __str__(self):#{{{
        return self.name + ": " + '\n'.join([str(q) for q in self.questions])#}}}

    def store(self):
        cursor.execute("INSERT into category (name, game_id) values (%s, %s)",
                       (self.name, self.game_id))
        self.dbid = cursor.lastrowid
        for q in self.questions:
            q.store( self.dbid )
 #}}}



def substitute_entity(match):
    ent = match.group(2)
    if match.group(1) == "#":
        return unichr(int(ent))
    else:
        cp = n2cp.get(ent)

        if cp:
            return unichr(cp)
        else:
            return match.group()

def decode_htmlentities(string):
    entity_re = re.compile("&(#?)(\d{1,5}|\w{1,8});")
    return entity_re.subn(substitute_entity, string)[0]



def main(argv):
    print argv[0]
    print argv[1]
    tf = open(argv[0], 'r')
    contents = tf.read()
    tf.close()
    game_id = int( argv[1] )
    soup = BeautifulSoup(contents)
    qs = getquestions( soup, game_id  )
    print "hey"
    print len(qs)
    for q in qs:
        q.store();


# testhtml = testfile();
# soup = BeautifulSoup(testhtml)
# qs = getquestions( soup )
# ts = soup.html.findAll('table',attrs={'class':'round'}, text=False)
# for q in qs:
#     q.store();
    #print q
#soup.html.findAll(id='clue_J_1_1')

if __name__ == '__main__': main( sys.argv[1:] )
