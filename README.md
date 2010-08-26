Kindle Widget Toolkit
=====================

The KWT (**Kindle Widget Toolkit**) is a set of KComponents to be used with the Kindle Development Kit to create compelling user interfaces for Active Content.

The components in KWT attempt to match the style of the KDK APIs and the underlying Java AWT, and should in most cases be a drop-in replacement for the default components in the KDK.

Features
--------

At the moment, KWT supports the following components.

**KWTCheckbox** - A simple toggle-able checkbox, or a selectable radio button, depending on whether it belongs to a **KWTCheckboxGroup**.

**KWTDiagram/KWTMutableDiagram** - A diagram, in both mutable and non-mutable versions, which can be added to a container and have text flow around it.

**KWTProgressBar** - A graphical representation of the progress towards completion of a particular task.

**KWTScrollPanel** - A KPanel that supports vertical scrolling, instead of the Kindle's usual Paged metaphor.

**KWTSelectableLabel** - A KLabel that can receive ActionEvents as if it were a KButton.

Further components are in active development.

Using KWT
---------

Unfortunately, the KDK does not enable app bundles to contain third-party JARs, so using KWT is not as simple as adding it to the classpath. There are two main approaches you can use.

### Copying the source

You can simply copy the widgets you want to use into your own package and compile it together with your application. This is the easiest way to get everything working, but you will have to manually apply updates as KWT adds features and improvements. Alternatively, you can include the KWT package as a git submodule in your repository.

### Use the JAR
Although the KDK does not enable its app bundles to contain third-party JARs, you can add a target to your **build.xml** that copies the required Class files into your own JAR. Updating KWT then becomes simply a matter of dropping in the latest release. You can see an example of such a target in the sample project distributed together with KWT.

Documentation
-------------

You can view a full JavaDoc API Reference for KWT [here](http://s3.amazonaws.com/kwt-dev/javadoc/index.html), or you can generate one yourself using the **generate-public-javadoc** build target.

License
-------

KWT is distributed under a permissive Apache 2.0 License (see our LICENSE file). As such, it can safely be used in both proprietary and free Kindle applications.

Contributing
------------

KWT is still in the early stages of development, and there are many great components still to be added. If you have some bug fixes or new components you would like included in KWT, please feel free to send a pull request. If you have bug reports, ideas for new features, or general feedback, please use GitHub's Issue tracker to let us know.
