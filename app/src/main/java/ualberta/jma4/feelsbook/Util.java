package ualberta.jma4.feelsbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Util
{
    public static void writeObjectToFile(Object obj, String fileName)
    {
        File file = new File(fileName);
        FileOutputStream outputStream;
        try
        {
            outputStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
            objectStream.writeObject(obj);
            objectStream.flush();
            objectStream.close();
        }
        catch (IOException e)
        {
            System.out.print("Write Object Failed");
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile(String fileName)
    {
        File file = new File(fileName);
        FileInputStream inputStream;
        Object object = null;
        try
        {
            inputStream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            object = objectStream.readObject();
            objectStream.close();
        }
        catch (IOException e)
        {
            System.out.print("Read Object Failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            System.out.print("Didn't find this object");
            e.printStackTrace();
        }
        return object;
    }

    public static void saveObjectToSharepreference(SharedPreferences setting, Object object, String fileName)
    {
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(fileName, convertObjectToString(object));
        editor.commit();
    }

    public static Object loadObjectFromSharepreference(SharedPreferences setting, String fileName)
    {
        String str = setting.getString(fileName, null);
        if(str == null)
            return null;

        return convertStringToObject(str);
    }

    private static String convertObjectToString(Object object)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = null;
        try
        {
            ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
            objectStream.writeObject(object);
            objectStream.close();
            bytes = outputStream.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(bytes==null)
            throw new NullPointerException();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private static Object convertStringToObject(String str)
    {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.decode(str, Base64.DEFAULT));
        Object object = null;
        try
        {
            ObjectInputStream objectStream = new ObjectInputStream(inputStream);
            object = objectStream.readObject();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        if(object==null)
            throw new NullPointerException();

        return object;
    }
}
