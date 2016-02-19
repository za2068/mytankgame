#include "GameScene.h"  
#include "HelloWorldScene.h"  
#include "SimpleAudioEngine.h"  
using namespace CocosDenshion;  
  
Game::Game(): spFood(NULL),spHead(NULL)  
{  
  
}  
Game::~Game(){}  
Scene* Game::createScene()  
{  
    auto scene = Scene::create();  
    auto layer = Game::create();  
    scene->addChild(layer);  
    return scene;  
}  
  
SnakeNode* SnakeNode::create(int type)  
{  
    SnakeNode * pRet = new SnakeNode();  
    if(pRet && pRet->init(type) )  
    {  
        pRet->autorelease();  
        return pRet;  
    }  
    else  
    {  
        delete pRet;  
        pRet = NULL;  
        return NULL;  
    }  
}  
SnakeNode::SnakeNode() : m_row(0),m_col(0)  
{  
  
}  
SnakeNode::~SnakeNode()  
{  
  
}  
bool SnakeNode::init(int type)  
{  
    if(!Sprite::init())  
    {  
        return false;  
    }  
    //�������Ͳ�ͬ��ʼ����ͬ������  
    switch(type)  
    {  
    case 1://��ͷ  
        {  
            auto sprite = Sprite::create("redstar.png");  
            sprite->setAnchorPoint(Point::ZERO);//����ê��  
            this->addChild(sprite);  
            m_dir = ENUM_DIR::DIR_RIGHT;//�����ƶ�  
        }  
        break;  
    case 2://����  
        {  
            auto sprite = Sprite::create("greenstar.png");  
            sprite->setAnchorPoint(Point::ZERO);  
            this->addChild(sprite);  
        }  
        m_dir = ENUM_DIR::DIR_STOP;  
        break;  
    case 3://ʳ��  
        {  
            auto sprite = Sprite::create("yellowstar.png");  
            sprite->setAnchorPoint(Point::ZERO);  
            this->addChild(sprite);  
  
        }  
        m_dir = ENUM_DIR::DIR_STOP;  
        break;  
    }  
    return true;  
}  
  
void SnakeNode::setPositionRC(int row, int col)//���ýڵ������  
{  
    this->m_row = row;  
    this->m_col = col;  
    setPosition(Point(m_col*32,m_row*32));  
}  
 bool Game::init()  
 {  
     if(!Layer::init())  
     {  
         return false;  
     }  
     //��ӵ�ͼ  
     auto draw = DrawNode::create();  
     draw->setAnchorPoint(Point::ZERO);  
     draw->setPosition(Point::ZERO);  
     this->addChild(draw);  
     for(int i = 0; i < 11; i++)  
     {  
         draw->drawSegment(Point(0,32*i),Point(320,32*i),1,Color4F(1,1,1,1));//���10�����������  
         draw->drawSegment(Point(32*i,0),Point(32*i,320),1,Color4F(1,1,1,1));//���10�����������  
     }  
     //�����ͷ  
     spHead = SnakeNode::create(1);  
     spHead->setPositionRC(0,0);  
     this->addChild(spHead);  
       
     CCLog("spHead  %d,%d",spHead->m_col,spHead->m_row);  
     //�������  
     //���ʳ��  
     spFood = SnakeNode::create(3);  
     int row = rand()%10;  
     int col = rand()%10;  
     spFood->setPositionRC(row,col);  
     CCLog(" spFood %d,%d",spFood->m_col,spFood->m_row);  
     this->addChild(spFood);  
  
     auto size = Director::getInstance()->getWinSize();  
     //��ӱ���  
     auto spriteBK = Sprite::create("menuback.png");  
     spriteBK->setPosition(Point(size.width/2,size.height/2));  
     spriteBK->setOpacity(75);  
     this->addChild(spriteBK);  
  
     //������ʾ  
     m_score = 0;  
     auto labelScore = Label::create("Score is 0","����",25);  
     labelScore->setTag(110);  
     labelScore->setPosition(Point(size.width-80,size.height -50 ));  
     this->addChild(labelScore);  
  
     //���ذ�ť  
    auto menuItemBack=MenuItemFont::create("Back", CC_CALLBACK_1(Game::menuCallBack,this));  
    auto menu=Menu::create(menuItemBack,NULL);  
    menu->setPosition(Point::ZERO);  
    menuItemBack->setPosition(Point(size.width-menuItemBack->getContentSize().width-50,menuItemBack->getContentSize().height+10));  
    this->addChild(menu);  
    //�ƻ�����  
    this->schedule(schedule_selector(Game::gameLogic),0.5);  
    //�����û������¼�����  
    auto listener = EventListenerTouchOneByOne::create();  
    listener->setSwallowTouches(true);//�����´������򵥵���˵������������sprite ,A �� B��A����B���£�λ���ص���������A��ʱ��B�����ܵ�Ӱ��   
    //listener->setSwallowTouches(false)��֮�����´��ݴ���������AҲ���ڴ�����B  
  
    listener->onTouchBegan= [&](Touch* t, Event* e){  
      
        //�ı�̰�����ƶ��ķ���  
        int col = t->getLocation().x/32;  
        int row = t->getLocation().y/32;  
        CCLog("your touchbegin %f,%f", t->getLocation().x,t->getLocation().y);  
        int spHeadCol = spHead->getPositionX()/32;//��  
        int spHeadRow = spHead->getPositionY()/32;//��  
        if(abs(spHeadCol - col)> abs(spHeadRow - row))//������һ��λ�ú��λ������ͷ����еĲ�ֵ�����еĲ�ֵ����ִ�������ƶ�  
        {  
            if (spHeadCol < col)//��Ӱ����������ƶ�  
            {  
                spHead->m_dir = ENUM_DIR::DIR_RIGHT;  
            }  
            else  
            {  
                spHead->m_dir = ENUM_DIR::DIR_LEFT;  
            }  
        }  
        else//�еĲ�ֵ�����еĲ�ֵ  
        {  
            if(spHeadRow < row)//��Ӱ��������µ��ƶ�  
            {  
                spHead->m_dir = ENUM_DIR::DIR_UP;  
            }  
            else  
            {  
                spHead->m_dir = ENUM_DIR::DIR_DOWN;  
            }  
        }  
        return true;       
    };  
    _eventDispatcher->addEventListenerWithSceneGraphPriority(listener,this);  
     return true;  
 }  
 void Game::gameLogic(float t)  
 {  
     moveBody();//�ƶ��������нڵ�  
     //��ͷ�ƶ�  
     switch (spHead->m_dir)  
     {  
     case ENUM_DIR::DIR_RIGHT:  
         spHead->runAction(MoveBy::create(0.3,Point(32,0)));//�����ƶ�32�����أ�Ҳ���������ƶ�һ����λ  
         spHead->m_col++;  
         break;  
     case ENUM_DIR::DIR_LEFT:  
           spHead->runAction(MoveBy::create(0.3, Point(-32,0)));//�����ƶ�32������  
           spHead->m_col--;  
           break;  
     case ENUM_DIR::DIR_DOWN:  
           spHead->runAction(MoveBy::create(0.3, Point(0,-32)));  
           spHead->m_row--;  
           break;  
     case ENUM_DIR::DIR_UP:  
            spHead->runAction(MoveBy::create(0.3, Point(0,32)));  
            spHead->m_row++;  
            break;  
     default:  
         break;  
     }  
      //��ײ���  
    if(spHead->m_row==spFood->m_row&&  
       spHead->m_col==spFood->m_col)  
    {  //��Ч�Ĳ���  
        SimpleAudioEngine::getInstance()->playEffect("eat.wav");  
        //��������  
        this->m_score+=100;  
        Label * label=(Label *)this->getChildByTag(110);  
        char strscore[20];  
        sprintf(strscore, "Score is:%d",m_score);  
        label->setString(strscore);  
      //ʳ������µ�λ��  
        int row=rand()%10;  
        int col=rand()%10;  
        spFood->setPositionRC(row,col);  
      //��ӽڵ�  
        newBody();  
    }  
  
 }  
  
 void Game::newBody()  
 {  
     auto bodynode = SnakeNode::create(2);//�½�����ڵ�  
     //��������ڵ�ķ��������  
     if(allBody.size()>0)//������ڵ�  
     {  
         //���һ������Ľڵ�  
         auto lastbody = allBody.at(allBody.size()-1);  
         bodynode->m_dir = lastbody->m_dir;  
  
         switch (bodynode->m_dir)  
         {  
         case ENUM_DIR::DIR_UP:  
             bodynode->setPositionRC(lastbody->m_row-1,lastbody->m_col);  
             break;  
        case ENUM_DIR::DIR_DOWN:  
            bodynode->setPositionRC(lastbody->m_row+1, lastbody->m_col);  
            break;  
        case ENUM_DIR::DIR_LEFT:  
            bodynode->setPositionRC(lastbody->m_row, lastbody->m_col+1);  
            break;  
        case ENUM_DIR::DIR_RIGHT:  
            bodynode->setPositionRC(lastbody->m_row, lastbody->m_col-1);  
            break;  
         default:  
             break;  
         }  
     }else  
     {  
         bodynode->m_dir = spHead->m_dir;  
         switch (bodynode->m_dir)  
         {  
            case ENUM_DIR::DIR_UP:  
                bodynode->setPositionRC(spHead->m_row-1, spHead->m_col);  
                break;  
            case ENUM_DIR::DIR_DOWN:  
                bodynode->setPositionRC(spHead->m_row+1, spHead->m_col);  
                break;  
            case ENUM_DIR::DIR_LEFT:  
                bodynode->setPositionRC(spHead->m_row, spHead->m_col+1);  
                break;  
            case ENUM_DIR::DIR_RIGHT:  
                bodynode->setPositionRC(spHead->m_row, spHead->m_col-1);  
                break;  
            default:  
                break;  
         }  
     }  
     //��ӽڵ㵽��ǰͼ��  
     this->addChild(bodynode);  
     //��ӽڵ㵽������  
     allBody.pushBack(bodynode);  
 }  
  
void Game::moveBody()//�ƶ����е�����ڵ�  
{  
    if(allBody.size() == 0)  
    {  
        return;  
    }  
    for (auto bodynode:allBody)  
    {  
        switch (bodynode->m_dir)  
        {  
        case ENUM_DIR::DIR_RIGHT:  
            bodynode->runAction(MoveBy::create(0.3, Point(32,0)));  
            bodynode->m_col++;  
            break;  
        case ENUM_DIR::DIR_LEFT:  
            bodynode->runAction(MoveBy::create(0.3, Point(-32,0)));  
            bodynode->m_col--;  
            break;  
        case ENUM_DIR::DIR_DOWN:  
            bodynode->runAction(MoveBy::create(0.3, Point(0,-32)));  
            bodynode->m_row--;  
            break;  
        case ENUM_DIR::DIR_UP:  
            bodynode->runAction(MoveBy::create(0.3, Point(0,32)));  
            bodynode->m_row++;  
            break;  
        default:  
            break;  
        }  
    }  
    //�ƶ���ɺ󣬸ı�ÿ��body�ķ���  
    for(int i = allBody.size()-1;i > 0; i--)  
    {  
        //ÿ���ڵ�ķ������Ϊ��ǰһ���ڵ�ķ���  
        allBody.at(i)->m_dir = allBody.at(i-1)->m_dir;  
    }  
    allBody.at(0)->m_dir = spHead->m_dir;  
  
  
  
}  
void Game::menuCallBack(Ref* object)  
{  
    auto scene = HelloWorld::createScene();  
    Director::getInstance()->replaceScene(scene);  
}  