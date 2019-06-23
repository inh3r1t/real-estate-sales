export default class LastMayday {
  palette(bg, code, logo, contact, title, content, articleTitleUrl1, articleTitleUrl2, articleTitleUrl3, articleTitleUrl4, articleTitleUrl5, articleTitleUrl6) {
    return ({
      width: '680rpx',
      height: '1246rpx',
      background: bg, //"./../../lib/image/bz_bg.png",//bg
      views: [{
          type: 'image',
          url: articleTitleUrl1,
          css: {
            top: `${10}rpx`,
            left: `${10}rpx`,
            mode: 'scaleToFill',
            width: '320rpx',
            height: '230rpx',
            borderWidth: '5rpx',
            borderColor: '#fff'
            /*rotate: rotate,*/
            // borderRadius: borderRadius,
          },
        },
        {
          type: 'image',
          url: articleTitleUrl2,
          css: {
            top: `${10}rpx`,
            left: `${350}rpx`,
            mode: 'scaleToFill',
            width: '320rpx',
            height: '230rpx',
            borderWidth: '5rpx',
            borderColor: '#fff'
            /*rotate: rotate,*/
            // borderRadius: borderRadius,
          },
        },
        {
          type: 'image',
          url: articleTitleUrl3,
          css: {
            top: `${260}rpx`,
            left: `${10}rpx`,
            mode: 'scaleToFill',
            width: '320rpx',
            height: '230rpx',
            borderWidth: '5rpx',
            borderColor: '#fff'
            /*rotate: rotate,*/
            // borderRadius: borderRadius,
          },
        },
        {
          type: 'image',
          url: articleTitleUrl4,
          css: {
            top: `${260}rpx`,
            left: `${350}rpx`,
            mode: 'scaleToFill',
            width: '320rpx',
            height: '230rpx',
            borderWidth: '5rpx',
            borderColor: '#fff'
            /*rotate: rotate,*/
            // borderRadius: borderRadius,
          },
        },
        {
          type: 'image',
          url: articleTitleUrl5,
          css: {
            top: `${510}rpx`,
            left: `${10}rpx`,
            mode: 'scaleToFill',
            width: '320rpx',
            height: '230rpx',
            borderWidth: '5rpx',
            borderColor: '#fff'
            /*rotate: rotate,*/
            // borderRadius: borderRadius,
          },
        },
        {
          type: 'image',
          url: articleTitleUrl6,
          css: {
            top: `${510}rpx`,
            left: `${350}rpx`,
            mode: 'scaleToFill',
            width: '320rpx',
            height: '230rpx',
            borderWidth: '5rpx',
            borderColor: '#fff'
            /*rotate: rotate,*/
            // borderRadius: borderRadius,
          },
        },
        {
          type: 'image',
          url: logo,
          css: {
            top: `${0}rpx`,
            left: `${50}rpx`,
            width: '150rpx',
            height: '50rpx'
          },
        }, 
        {
          type: 'text',
          text: contact,
          css: {
            fontSize: '26rpx',
            bottom: `${100}rpx`,
            left: `${50}rpx`,
            color: '#545458',
            width: '550rpx',
            fontWeight: 'bold',
            maxLines: 1,
          }
        },
        {
          type: 'text',
          text: title,
          css: {
            fontSize: '34rpx',
            fontWeight: 'bold',
            bottom: `${160}rpx`,
            left: `${50}rpx`,
            color: '#545458',
            width: '550rpx',
            maxLines: 1,
          }
        },
        {
          type: 'text',
          text: content,
          css: {
            fontSize: '26rpx',
            top: `${790}rpx`,
            left: `${50}rpx`,
            color: '#fff',
            width: '550rpx',
            maxLines: 4,
            lineHeight: '55rpx'
          }
        },
        {
          type: 'text',
          text: '欢迎长按小程序码识别该房源',
          css: {
            fontSize: '20rpx',
            bottom: `${5}rpx`,
            left: `${50}rpx`,
            // right: `${80}rpx`,
            color: '#ccc',
            width: '400rpx',
            maxLines: 1,
            lineHeight: '45rpx'
          }
        },
        {
          type: 'image',
          url: code,
          css: {
            bottom: `${60}rpx`,
            right: `${50}rpx`,
            width: '120rpx',
            height: '120rpx',

          },
        }
      ],
    });
  }
}