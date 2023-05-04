# StoreOverlay Exemple Usage

## Configure the project

### Create a woosmap account
If you don't have a Woosmap account first create it on [our website](https://www.woosmap.com)
After your subscription you have to push your data on our backend to be able to display them on your mobile appplication
Report to our [documentation](https://developers.woosmap.com) to know how to do it or in this blog post [Manage Assets](https://community.woosmap.com/manage-assets/upload-woosmapjson-file/)

### Configure the mobile app
To use this code exemple first set your woosmap's private key in the file `AndroidManifest.xml

```
        <meta-data
            android:name="com.woosmap.woosmapsdk.privateKey"
            android:value=" put your Woosmap private key here " />
```
