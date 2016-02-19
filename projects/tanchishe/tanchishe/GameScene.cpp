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
    //根据类型不同初始化不同的纹理  
    switch(type)  
    {  
    case 1://蛇头  
        {  
            auto sprite = Sprite::create("redstar.png");  
            sprite->setAnchorPoint(Point::ZERO);//设置锚点  
            this->addChild(sprite);  
            m_dir = ENUM_DIR::DIR_RIGHT;//向右移动  
        }  
        break;  
    case 2://身体  
        {  
            auto sprite = Sprite::create("greenstar.png");  
            sprite->setAnchorPoint(Point::ZERO);  
            this->addChild(sprite);  
        }  
        m_dir = ENUM_DIR::DIR_STOP;  
        break;  
    case 3://食物  
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
  
void SnakeNode::setPositionRC(int row, int col)//设置节点的坐标  
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
     //添加地图  
     auto draw = DrawNode::create();  
     draw->setAnchorPoint(Point::ZERO);  
     draw->setPosition(Point::ZERO);  
     this->addChild(draw);  
     for(int i = 0; i < 11; i++)  
     {  
         draw->drawSegment(Point(0,32*i),Point(320,32*i),1,Color4F(1,1,1,1));//添加10条横向的线条  
         draw->drawSegment(Point(32*i,0),Point(32*i,320),1,Color4F(1,1,1,1));//添加10条纵向的线条  
     }  
     //添加蛇头  
     spHead = SnakeNode::create(1);  
     spHead->setPositionRC(0,0);  
     this->addChild(spHead);  
       
     CCLog("spHead  %d,%d",spHead->m_col,spHead->m_row);  
     //添加身体  
     //添加食物  
     spFood = SnakeNode::create(3);  
     int row = rand()%10;  
     int col = rand()%10;  
     spFood->setPositionRC(row,col);  
     CCLog(" spFood %d,%d",spFood->m_col,spFood->m_row);  
     this->addChild(spFood);  
  
     auto size = Director::getInstance()->getWinSize();  
     //添加背景  
     auto spriteBK = Sprite::create("menuback.png");  
     spriteBK->setPosition(Point(size.width/2,size.height/2));  
     spriteBK->setOpacity(75);  
     this->addChild(spriteBK);  
  
     //分数显示  
     m_score = 0;  
     auto labelScore = Label::create("Score is 0","宋体",25);  
     labelScore->setTag(110);  
     labelScore->setPosition(Point(size.width-80,size.height -50 ));  
     this->addChild(labelScore);  
  
     //返回按钮  
    auto menuItemBack=MenuItemFont::create("Back", CC_CALLBACK_1(Game::menuCallBack,this));  
    auto menu=Menu::create(menuItemBack,NULL);  
    menu->setPosition(Point::ZERO);  
    menuItemBack->setPosition(Point(size.width-menuItemBack->getContentSize().width-50,menuItemBack->getContentSize().height+10));  
    this->addChild(menu);  
    //计划任务  
    this->schedule(schedule_selector(Game::gameLogic),0.5);  
    //加入用户触摸事件监听  
    auto listener = EventListenerTouchOneByOne::create();  
    listener->setSwallowTouches(true);//不向下触摸，简单点来说，比如有两个sprite ,A 和 B，A在上B在下（位置重叠），触摸A的时候，B不会受到影响   
    //listener->setSwallowTouches(false)反之，向下传递触摸，触摸A也等于触摸了B  
  
    listener->onTouchBegan= [&](Touch* t, Event* e){  
      
        //改变贪吃蛇移动的方向  
        int col = t->getLocation().x/32;  
        int row = t->getLocation().y/32;  
        CCLog("your touchbegin %f,%f", t->getLocation().x,t->getLocation().y);  
        int spHeadCol = spHead->getPositionX()/32;//列  
        int spHeadRow = spHead->getPositionY()/32;//行  
        if(abs(spHeadCol - col)> abs(spHeadRow - row))//如果点击一个位置后该位置与蛇头相比列的差值大于行的差值，就执行左右移动  
        {  
            if (spHeadCol < col)//列影响的是左右移动  
            {  
                spHead->m_dir = ENUM_DIR::DIR_RIGHT;  
            }  
            else  
            {  
                spHead->m_dir = ENUM_DIR::DIR_LEFT;  
            }  
        }  
        else//行的差值大于列的差值  
        {  
            if(spHeadRow < row)//行影响的是上下的移动  
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
     moveBody();//移动身体所有节点  
     //蛇头移动  
     switch (spHead->m_dir)  
     {  
     case ENUM_DIR::DIR_RIGHT:  
         spHead->runAction(MoveBy::create(0.3,Point(32,0)));//向右移动32个像素，也就是向右移动一个单位  
         spHead->m_col++;  
         break;  
     case ENUM_DIR::DIR_LEFT:  
           spHead->runAction(MoveBy::create(0.3, Point(-32,0)));//向左移动32个像素  
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
      //碰撞检测  
    if(spHead->m_row==spFood->m_row&&  
       spHead->m_col==spFood->m_col)  
    {  //音效的播放  
        SimpleAudioEngine::getInstance()->playEffect("eat.wav");  
        //分数增加  
        this->m_score+=100;  
        Label * label=(Label *)this->getChildByTag(110);  
        char strscore[20];  
        sprintf(strscore, "Score is:%d",m_score);  
        label->setString(strscore);  
      //食物产生新的位置  
        int row=rand()%10;  
        int col=rand()%10;  
        spFood->setPositionRC(row,col);  
      //添加节点  
        newBody();  
    }  
  
 }  
  
 void Game::newBody()  
 {  
     auto bodynode = SnakeNode::create(2);//新建身体节点  
     //设置这个节点的方向和坐标  
     if(allBody.size()>0)//有身体节点  
     {  
         //最后一个身体的节点  
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
     //添加节点到当前图层  
     this->addChild(bodynode);  
     //添加节点到集合中  
     allBody.pushBack(bodynode);  
 }  
  
void Game::moveBody()//移动所有的身体节点  
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
    //移动完成后，改变每个body的方向  
    for(int i = allBody.size()-1;i > 0; i--)  
    {  
        //每个节点的方向调整为它前一个节点的方向  
        allBody.at(i)->m_dir = allBody.at(i-1)->m_dir;  
    }  
    allBody.at(0)->m_dir = spHead->m_dir;  
  
  
  
}  
void Game::menuCallBack(Ref* object)  
{  
    auto scene = HelloWorld::createScene();  
    Director::getInstance()->replaceScene(scene);  
}  