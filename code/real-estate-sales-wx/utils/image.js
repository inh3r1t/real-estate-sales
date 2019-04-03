export default class LastMayday {
  palette(bg, articleTitleUrl, code, logo, title, content) {
    return ({
      width: '650rpx',
      height: '1000rpx',
      background: bg, //"./../../lib/image/bz_bg.png",//bg
      views: [{
          type: 'text',
          text: title, 
          css: {
            fontSize: '36rpx',
            top: `${520}rpx`,
            left: `${50}rpx`,
            color: '#fff',
            width: '550rpx',
            maxLines: 1,
          }
        },
        {
          type: 'text',
          text: content, 
          css: {
            fontSize: '26rpx',
            top: `${600}rpx`,
            left: `${50}rpx`,
            color: '#fff',
            width: '550rpx',
            maxLines: 5,
            lineHeight: '45rpx'
          }
        },
        {
          type: 'text',
          text: '长按小程序码阅读图文详情',
          css: {
            fontSize: '26rpx',
            bottom: `${30}rpx`,
            right: `${200}rpx`,
            color: '#ccc',
            width: '160rpx',
            maxLines: 2,
            lineHeight: '45rpx'
          }
        },
        {
          type: 'image',
          url: code, 
          css: {
            bottom: `${20}rpx`,
            right: `${50}rpx`,
            width: '120rpx',
            height: '120rpx',

          },
        },
        {
          type: 'image',
          url: logo, 
          css: {
            bottom: `${20}rpx`,
            left: `${50}rpx`,
            width: '120rpx',
            height: '120rpx',
            borderRadius: '20rpx'
          },
        },
        {
          type: 'image',
          url: articleTitleUrl, 
          css: {
            top: `${90}rpx`,
            left: `${50}rpx`,
            mode: 'scaleToFill',
            width: '550rpx',
            height: '400rpx',
            borderWidth: '5rpx',
            borderColor: '#fff'
            /*rotate: rotate,*/
            // borderRadius: borderRadius,
          },
        }
      ],
    });
  }
}