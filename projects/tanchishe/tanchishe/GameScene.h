//游戏画面  
#ifndef __snakegame__GameScene__  
#define __snakegame__GameScene__  
#include "cocos2d.h"  
USING_NS_CC;  
//定义一个游戏的枚举类型来标识贪吃蛇的移动方向  
enum  class ENUM_DIR  
{  
    DIR_UP,  
    DIR_DOWN,  
    DIR_LEFT,  
    DIR_RIGHT,  
    DIR_STOP  
};  
//蛇精灵  
class SnakeNode:public Sprite//封装节点  
{  
public:  
      
    ENUM_DIR m_dir;//移动方向  
    int nodeType;//当前类型 1：蛇头2：身体3食物  
    int m_row, m_col; //当前节点的行列坐标  
  
    SnakeNode();  
    ~SnakeNode();  
    static SnakeNode* create(int type);  
    virtual bool init(int type);  
    void setPositionRC(int row, int col);//设置节点的坐标  
};  
//游戏界面图层  
class Game:public Layer{  
public:  
    SnakeNode* spFood;//食物  
    SnakeNode* spHead;//蛇头  
    int m_score; //分  
    Game();  
    ~Game();  
    Vector<SnakeNode*>allBody;//身体，身体长度是不确定的，不确定的数组利用向量Vector  
    static Scene* createScene();  
    CREATE_FUNC(Game);  
    virtual bool init();  
    void menuCallBack(Ref* object);  
    void  gameLogic(float);  
    void newBody();//添加一个新的身体节点  
    void moveBody();//移动所有的身体节点  
};  
#endif  